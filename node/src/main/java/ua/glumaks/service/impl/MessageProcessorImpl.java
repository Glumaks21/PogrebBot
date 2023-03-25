package ua.glumaks.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.glumaks.domain.*;
import ua.glumaks.exceptions.FileNotFoundException;
import ua.glumaks.rpository.RawDataRepo;
import ua.glumaks.rpository.AppUserRepo;
import ua.glumaks.service.*;
import ua.glumaks.service.jms.AnswerProducerService;

import java.util.Optional;

import static ua.glumaks.domain.UserState.BASIC_STATE;
import static ua.glumaks.service.Command.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageProcessorImpl implements MessageProcessor {

    private final RawDataRepo rawDataRepo;
    private final AppUserRepo userRepo;

    private final FileService fileService;
    private final AppUserService appUserService;
    private final AnswerProducerService producerService;

    @Override
    public void processTextMessage(Update update) {
        String output = null;
        try {
            saveRawData(update);

            Message message = update.getMessage();
            AppUser appUser = appUserService.findOrSaveAppUser(message.getFrom());

            Optional<Command> cmd = Command.forCommand(message.getText());
            if (cmd.isEmpty()) {
                output = "Unknown command! To check command list enter /help";
                return;
            }

            output = processCommand(appUser, cmd.get());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            output = "Ooops...something goes wrong";
        } finally {
            sendAnswer(output, update);
        }
    }

    private void saveRawData(Update update) {
        var rawData = RawData.builder()
                .update(update)
                .build();

        rawDataRepo.save(rawData);
    }

    private String processCommand(AppUser appUser, Command command) {
        if (CANCEL.equals(command)) {
            return cancel(appUser, userRepo);
        }

        UserState userState = appUser.getState();
        return switch (userState) {
            case BASIC_STATE -> processBasicCommands(appUser, command);
            case WAIT_FOR_EMAIL_STATE -> ""; //TODO add email processing
            default -> {
                log.error("Unknown user state: " + userState);
                throw new EnumConstantNotPresentException(UserState.class,
                        "User state: " + userState);
            }
        };
    }

    private String cancel(AppUser appUser, AppUserRepo userRepository) {
        appUser.setState(BASIC_STATE);
        userRepository.save(appUser);
        return "Successful";
    }

    private String processBasicCommands(AppUser appUser, Command command) {
        return switch (command) {
            case START -> start();
            case REGISTRATION -> "НЕ НА ЧАСІ!";      //TODO add registration
            case HELP -> help();
            default -> {
                log.error("Unknown command: " + command);
                yield "Unknown command! To check command list enter /help";
            }
        };
    }

    private String start() {
        return "Hello! To check command list enter /help";
    }

    private String help() {
        return """
                List of accessible commands:
                /cancel - cancel execution of the current command
                /registration - register a client
                """;
    }

    @Override
    public void processDocMessage(Update update) {
        saveRawData(update);

        Message message = update.getMessage();
        AppUser appUser = appUserService.findOrSaveAppUser(message.getFrom());

        if (isNotAllowedToSendContent(update, appUser)) {
            return;
        }

        String answer;
        try {
            AppDocument doc = fileService.downloadAppDocument(message);
            String link = fileService.generateLink(doc.getId(), LinkType.GET_DOC);
            answer = "Document is successfully uploaded! Link for downloading: " + link;
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
            answer = "Sorry, failure to upload file";
        }
        sendAnswer(answer, update);
    }

    @Override
    public void processPhotoMessage(Update update) {
        saveRawData(update);

        Message message = update.getMessage();
        AppUser appUser = appUserService.findOrSaveAppUser(message.getFrom());

        if (isNotAllowedToSendContent(update, appUser)) {
            return;
        }

        String answer;
        try {
            AppPhoto photo = fileService.downloadAppPhoto(message);
            String link = fileService.generateLink(photo.getId(), LinkType.GET_PHOTO);
            answer = "Photo is successfully loaded! Link for downloading: " + link;
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
            answer = "Sorry, failure to upload file";
        }
        sendAnswer(answer, update);
    }

    private boolean isNotAllowedToSendContent(Update update, AppUser appUser) {
        UserState userState = appUser.getState();
        if (!appUser.isActive()) {
            sendAnswer("Register or activate your account to load a content", update);
            return true;
        } else if (!BASIC_STATE.equals(userState)) {
            sendAnswer("Cancel the current operation with /cancel to load a content", update);
            return true;
        }

        return false;
    }


    private void sendAnswer(String text, Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText(text);
        producerService.produceAnswer(sendMessage);
    }

}
