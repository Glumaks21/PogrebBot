package ua.glumaks.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.File;
import ua.glumaks.domain.AppDocument;
import ua.glumaks.domain.BinaryContent;
import ua.glumaks.exceptions.FileNotFoundException;
import ua.glumaks.repostiroty.AppDocumentRepo;
import ua.glumaks.service.AppDocumentService;

import java.text.MessageFormat;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultAppDocumentService implements AppDocumentService {

    @Value("${telegram.bot.token}")
    private String token;

    @Value("${telegram.bot.api.url}")
    private String url;

    private final RestTemplate restTemplate;
    private final AppDocumentRepo repo;


    @Override
    public AppDocument downloadAppDocument(Document document) {
        try {
            byte[] fileInByte = getFileBytes(document.getFileId());
            BinaryContent binaryContent = BinaryContent.builder()
                    .fileAsArrayOfBytes(fileInByte)
                    .build();

            AppDocument appDocument = AppDocument.builder()
                    .telegramFieldId(document.getFileId())
                    .docName(document.getFileName())
                    .binaryContent(binaryContent)
                    .mimeType(document.getMimeType())
                    .fileSize(document.getFileSize())
                    .build();
            return repo.save(appDocument);
        } catch (Exception e) {
            throw new FileNotFoundException(e);
        }
    }

    private String getFilePath(String fileId) {
        ResponseEntity<String> response = restTemplate.exchange(
                MessageFormat.format("{0}bot{1}/getFile?file_id={2}", url, token, fileId),
                HttpMethod.GET,
                null,
                ParameterizedTypeReference.forType(String.class)
        );

        JSONObject jsonObject = new JSONObject(response.getBody());
        return String.valueOf(jsonObject
                .getJSONObject("result")
                .getString("file_path")
        );
    }


    private byte[] getFileBytes(String fileId) {
        String filePath = getFilePath(fileId);
        return restTemplate.execute(
                MessageFormat.format("{0}file/bot{1}/{2}", url, token, filePath),
                HttpMethod.GET,
                null,
                response -> response
                        .getBody()
                        .readAllBytes()
        );
    }

}
