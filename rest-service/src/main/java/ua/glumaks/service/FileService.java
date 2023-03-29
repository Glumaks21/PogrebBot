package ua.glumaks.service;

import org.springframework.core.io.FileSystemResource;
import ua.glumaks.domain.AppDocument;
import ua.glumaks.domain.AppPhoto;
import ua.glumaks.domain.BinaryContent;

public interface FileService {

    AppDocument getDocument(String id);
    AppPhoto getPhoto(String id);

}
