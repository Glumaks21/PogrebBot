package ua.glumaks.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.glumaks.entity.RawData;
import ua.glumaks.repostiroty.RawDataRepository;
import ua.glumaks.service.MainService;
import ua.glumaks.service.ProducerService;

@Service
@Slf4j
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {

    private final RawDataRepository repository;

    private final ProducerService producerService;

    @Override
    public void processTextMessage(Update update) {
        saveRawData(update);

        Message originMessage = update.getMessage();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(originMessage.getChatId());
        sendMessage.setText("Hello from node");

        producerService.produceAnswer(sendMessage);
    }

    private void saveRawData(Update update) {
        RawData rawData = RawData.builder()
                .update(update)
                .build();

        repository.save(rawData);
    }

}
