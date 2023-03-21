package ua.glumaks.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ua.glumaks.entity.AppUser;
import ua.glumaks.entity.RawData;
import ua.glumaks.entity.enums.UserState;
import ua.glumaks.repository.AppUserRepository;
import ua.glumaks.repostiroty.RawDataRepository;
import ua.glumaks.service.MainService;
import ua.glumaks.service.ProducerService;

import java.util.Optional;

import static ua.glumaks.entity.enums.UserState.*;

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

        var message = update.getMessage();
        var user = message.getFrom();
        var appUser = findOrSaveAppUser(user);

        var originMessage = update.getMessage();
        var sendMessage = new SendMessage();
        sendMessage.setChatId(originMessage.getChatId());
        sendMessage.setText("Hello from node");

        producerService.produceAnswer(sendMessage);
    }

    private AppUser findOrSaveAppUser(User telegramUser) {
        var userCandidate = userRepository.findByTelegramUserId(telegramUser.getId());

        if (userCandidate.isEmpty()) {
            AppUser user = AppUser.builder()
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
