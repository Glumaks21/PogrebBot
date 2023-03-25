package ua.glumaks.service;


import org.telegram.telegrambots.meta.api.objects.Message;
import ua.glumaks.domain.AppDocument;
import ua.glumaks.domain.AppPhoto;

public interface FileService {

    AppDocument downloadAppDocument(Message message);
    AppPhoto downloadAppPhoto(Message message);

    String generateLink(Long docId, LinkType linkType);
}
