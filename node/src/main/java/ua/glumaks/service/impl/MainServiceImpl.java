package ua.glumaks.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.glumaks.entity.AppUser;
import ua.glumaks.entity.RawData;
import ua.glumaks.repository.AppUserRepository;
import ua.glumaks.repostiroty.RawDataRepository;
import ua.glumaks.service.MainService;
import ua.glumaks.service.ProducerService;

import static ua.glumaks.entity.enums.UserState.BASIC_STATE;
import static ua.glumaks.entity.enums.UserState.WAIT_FOR_EMAIL_STATE;
import static ua.glumaks.service.enums.ServiceCommands.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {

    private final RawDataRepository rawDataRepository;
    private final ProducerService producerService;
    private final AppUserRepository userRepository;

    @Override
    public void processTextMessage(Update update) {
        saveRawData(update);

        var appUser = findOrSaveAppUser(update);
        var userState = appUser.getState();
        var text = update.getMessage().getText();
        var output = "";

        if (CANCEL.equals(text)) {
            output = cancelProcess(appUser);
        } else if (BASIC_STATE.equals(userState)) {
            output = processServiceCommands(appUser, text);
        } else if (WAIT_FOR_EMAIL_STATE.equals(userState)) {
            //TODO add email processing
        } else {
            log.error("Unknown user state: " + userState);
            output = "Shit happens! Enter /cancel and try again";
        }

        var chatId = update.getMessage().getChatId();
        sendAnswer(output, chatId);
    }

    @Override
    public void processDocMessage(Update update) {
        saveRawData(update);

        var appUser = findOrSaveAppUser(update);
        var chatId = update.getMessage().getChatId();

        if (isNotAllowedToSendContent(chatId, appUser)) {
            return;
        }

        //TODO add message loadind
        var answer = "Message is successfully loaded!(DOC)";
        sendAnswer(answer, chatId);
    }

    @Override
    public void processPhotoMessage(Update update) {
        saveRawData(update);

        var appUser = findOrSaveAppUser(update);
        var chatId = update.getMessage().getChatId();

        if (isNotAllowedToSendContent(chatId, appUser)) {
            return;
        }

        //TODO add message loadind
        var answer = "Message is successfully loaded!(PHOTO)";
        sendAnswer(answer, chatId);
    }

    private boolean isNotAllowedToSendContent(Long chatId, AppUser appUser) {
        var userState = appUser.getState();
        if (!appUser.isActive()) {
            var error = "Register or activate your account to load a content";
            sendAnswer(error, chatId);
            return true;
        } else if (!BASIC_STATE.equals(userState)) {
            var error = "Cancel the current operation with /cancel to load a content";
            sendAnswer(error, chatId);
            return true;
        }

        return false;
    }

    private void sendAnswer(String text, Long chatId) {
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        producerService.produceAnswer(sendMessage);
    }

    private String cancelProcess(AppUser user) {
        user.setState(BASIC_STATE);
        userRepository.save(user);
        return "Command canceled";
    }

    private String processServiceCommands(AppUser appUser, String cmd) {
        if (REGISTRATION.equals(cmd)) {
            //TODO add registration
            return "НЕ НА ЧАСІ!";
        } else if (HELP.equals(cmd)) {
            return help();
        } else if (START.equals(cmd)) {
            return "Hello! To check command list enter /help";
        } else {
            return "Unknown command! To check command list enter /help";
        }
    }

    private String help() {
        return """
                List of accessible commands:
                /cancel - cancel execution of the current command
                /registration - register a client
                """;
    }

    private AppUser findOrSaveAppUser(Update update) {
        var telegramUser = update.getMessage().getFrom();
        var userCandidate = userRepository.findByTelegramUserId(telegramUser.getId());

        if (userCandidate.isEmpty()) {
            var user = AppUser.builder()
                    .telegramUserId(telegramUser.getId())
                    .username(telegramUser.getUserName())
                    .firstName(telegramUser.getFirstName())
                    .lastName(telegramUser.getLastName())
                    //TODO change default value after registration
                    .isActive(true)
                    .state(BASIC_STATE)
                    .build();
            return userRepository.save(user);
        }

        return userCandidate.get();
    }

    private void saveRawData(Update update) {
        var rawData = RawData.builder()
                .update(update)
                .build();

        rawDataRepository.save(rawData);
    }

}
