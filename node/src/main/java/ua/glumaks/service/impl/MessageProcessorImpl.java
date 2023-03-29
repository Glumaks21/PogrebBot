package ua.glumaks.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.glumaks.domain.*;
import ua.glumaks.exceptions.FileNotFoundException;
import ua.glumaks.repository.AppUserRepo;
import ua.glumaks.repository.RawDataRepo;
import ua.glumaks.service.*;
import ua.glumaks.service.jms.ProducerService;
import ua.glumaks.service.state.State;
import ua.glumaks.util.StateSpringUtil;


import static ua.glumaks.domain.UserState.BASIC_STATE;
import static ua.glumaks.service.command.CommandType.*;
import static ua.glumaks.util.MessageUtils.createSendMessage;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageProcessorImpl implements MessageProcessor {

    private final RawDataRepo rawDataRepo;
    private final AppUserRepo userRepo;
    private final FileService fileService;
    private final AppUserService userService;
    private final ProducerService producer;


    @Override
    public void processTextMessage(Message message) {
        try {
            saveRawData(message);

            AppUser user = userService.findOrSaveAppUser(message.getFrom());
            UserState userState = user.getState();

            State state = StateSpringUtil.forType(userState);
            BotApiMethod<?> answer = state.process(user, message);

            producer.produce(answer);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            producer.produce(createSendMessage(
                    "Ooops...something goes wrong", message)
            );
        }
    }

    private void saveRawData(Message message) {
        RawData rawData = RawData.builder()
                .message(message)
                .build();

        rawDataRepo.save(rawData);
    }


    private BotApiMethod<?> cancel(AppUser user, Message message) {
        user.setState(BASIC_STATE);
        userRepo.save(user);

        String answer = "You sre successful rolled up to basic state";
        return createSendMessage(answer, message);
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

        producer.produce(createSendMessage(answer, message));
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

        producer.produce(createSendMessage(answer, message));
    }

}
