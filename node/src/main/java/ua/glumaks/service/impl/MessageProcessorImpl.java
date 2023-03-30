package ua.glumaks.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.glumaks.domain.*;
import ua.glumaks.exceptions.FileNotFoundException;
import ua.glumaks.repository.AppUserRepo;
import ua.glumaks.repository.RawDataRepo;
import ua.glumaks.service.*;
import ua.glumaks.service.state.State;
import ua.glumaks.util.StateSpringUtil;

import static ua.glumaks.domain.UserState.BASIC_STATE;
import static ua.glumaks.service.command.CommandType.CANCEL;
import static ua.glumaks.service.command.CommandType.REGISTRATION;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageProcessorImpl implements MessageProcessor {

    private final RawDataRepo rawDataRepo;
    private final AppUserRepo userRepo;
    private final FileService fileService;
    private final AppUserService userService;
    private final TelegramService telegramService;


    @Override
    public void processTextMessage(Message message) {
        try {
            saveRawData(message);

            AppUser user = userService.findOrSaveAppUser(message.getFrom());
            UserState userState = user.getState();

            State state = StateSpringUtil.forType(userState);
            state.process(user, message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            String answer = "Ooops...something goes wrong";
            telegramService.sendMessage(message.getChatId(), answer);
        }
    }

    private void saveRawData(Message message) {
        RawData rawData = RawData.builder()
                .message(message)
                .build();

        rawDataRepo.save(rawData);
    }

    @Override
    public void processDocMessage(Message message) {
        saveRawData(message);

        AppUser user = userService.findOrSaveAppUser(message.getFrom());

        String answer;
        if (user.getEmail() == null) {
            answer = "You need be registered to upload photos, enter " + REGISTRATION + " to register";
        } else if (user.getActivationCode() != null) {
            answer = "Follow the link in the letter sent to your email to activate your account";
        } else if (!BASIC_STATE.equals(user.getState())) {
            answer = "Cancel the current operation with " + CANCEL + " to load a content";
        } else {

            try {
                AppDocument doc = fileService.downloadAppDocument(message);
                String link = fileService.generateLink(doc.getId(), RestPathType.GET_DOC);
                answer = "Document is successfully uploaded! Link for downloading: " + link;
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                answer = "Ooops...something goes wrong";
            }

        }

        telegramService.sendMessage(message.getChatId(), answer);
    }

    @Override
    public void processPhotoMessage(Message message) {
        saveRawData(message);

        AppUser user = userService.findOrSaveAppUser(message.getFrom());

        String answer;
        if (user.getEmail() == null) {
            answer = "You need be registered to upload photos, enter " + REGISTRATION + " to register";
        } else if (user.getActivationCode() != null) {
            answer = "Follow the link in email address to activate your account";
        } else if (!BASIC_STATE.equals(user.getState())) {
            answer = "Cancel the current operation with " + CANCEL + " to load a content";
        } else {

            try {
                AppPhoto photo = fileService.downloadAppPhoto(message);
                String link = fileService.generateLink(photo.getId(), RestPathType.GET_PHOTO);
                answer = "Photo is successfully uploaded! Link for downloading: " + link;
            } catch (FileNotFoundException e) {
                log.error(e.getMessage(), e);
                answer = "Ooops...something goes wrong";
            }
        }

        telegramService.sendMessage(message.getChatId(), answer);
    }

}
