package ua.glumaks.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.telegram.telegrambots.facilities.filedownloader.TelegramFileDownloader;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.glumaks.domain.AppDocument;
import ua.glumaks.domain.AppPhoto;
import ua.glumaks.domain.BinaryContent;
import ua.glumaks.exceptions.FileNotFoundException;
import ua.glumaks.repository.AppDocumentRepo;
import ua.glumaks.repository.AppPhotoRepo;
import ua.glumaks.service.FileService;
import ua.glumaks.service.RestPathType;
import ua.glumaks.utils.CryptoUtil;

import java.io.IOException;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final AppDocumentRepo docRepo;
    private final AppPhotoRepo photoRepo;
    private final TelegramBotCommandApi api;

    @Value("${service.rest-service.url}")
    private String restServiceUrl;

    private final CryptoUtil hashUtil;


    @Override
    public AppDocument downloadAppDocument(Message message) {
        try {
            Document document = message.getDocument();
            byte[] fileInByte = getFileBytes(document.getFileId());
            AppDocument appDocument = convertToAppDocument(document, fileInByte);

            return docRepo.save(appDocument);
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);
            throw new FileNotFoundException(e);
        }
    }

    private byte[] getFileBytes(String fileId) throws TelegramApiException {
        GetFile getFile = new GetFile(fileId);
        File file = api.execute(getFile);

        TelegramFileDownloader downloader = new TelegramFileDownloader(api::getBotToken);
        try {
            return downloader
                    .downloadFileAsStream(file)
                    .readAllBytes();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new FileNotFoundException("Error is occurred while downloading the file", e);
        }
    }

    private AppDocument convertToAppDocument(Document document, byte[] bytes) {
        BinaryContent binaryContent = BinaryContent.builder()
                .fileAsArrayOfBytes(bytes)
                .build();
        return AppDocument.builder()
                .telegramFieldId(document.getFileId())
                .docName(document.getFileName())
                .binaryContent(binaryContent)
                .mimeType(document.getMimeType())
                .fileSize(document.getFileSize())
                .build();
    }

    @Override
    public AppPhoto downloadAppPhoto(Message message) {
        Assert.isTrue(message.hasPhoto(), "Message doesn't contain photos");

        try {
            //TODO add multiple photo processing
            List<PhotoSize> photos = message.getPhoto();
            PhotoSize photo = photos.get(photos.size() - 1);
            byte[] photoBytes = getFileBytes(photo.getFileId());
            AppPhoto appPhoto = convertToAppPhoto(photo, photoBytes);

            return photoRepo.save(appPhoto);
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);
            throw new FileNotFoundException(e);
        }
    }

    private AppPhoto convertToAppPhoto(PhotoSize photo, byte[] bytes) {
        BinaryContent binaryContent = BinaryContent.builder()
                .fileAsArrayOfBytes(bytes)
                .build();
        return AppPhoto.builder()
                .telegramFieldId(photo.getFileId())
                .binaryContent(binaryContent)
                .fileSize(photo.getFileSize())
                .build();
    }


    @Override
    public String generateLink(Long docId, RestPathType linkType) {
        return restServiceUrl + "/" + linkType +
                "?id=" + hashUtil.encode(docId.toString());
    }


}
