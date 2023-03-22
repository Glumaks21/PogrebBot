package ua.glumaks.service;


import org.telegram.telegrambots.meta.api.objects.Document;
import ua.glumaks.domain.AppDocument;

public interface AppDocumentService {

    AppDocument downloadAppDocument(Document document);

}
