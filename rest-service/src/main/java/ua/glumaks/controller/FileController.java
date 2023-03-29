package ua.glumaks.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.glumaks.domain.AppDocument;
import ua.glumaks.domain.AppPhoto;
import ua.glumaks.domain.BinaryContent;
import ua.glumaks.service.FileService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    @GetMapping("/doc")
    ResponseEntity<byte[]> getDocument(@RequestParam("id") String id) {
        //TODO add controller advice
        AppDocument document = fileService.getDocument(id);
        if (document == null) {
            throw new IllegalArgumentException("id: " + id);
        }

        BinaryContent binaryContent = document.getBinaryContent();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(document.getMimeType()))
                .header("Content-disposition", "attachment; filename=" + document.getDocName())
                .body(binaryContent.getFileAsArrayOfBytes());
    }

    @GetMapping("/photo")
    ResponseEntity<byte[]> getPhoto(@RequestParam("id") String id) {
        //TODO add controller advice
        AppPhoto photo = fileService.getPhoto(id);
        if (photo == null) {
            throw new IllegalArgumentException("id: " + id);
        }

        BinaryContent binaryContent = photo.getBinaryContent();
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header("Content-disposition", "attachment;")
                .body(binaryContent.getFileAsArrayOfBytes());
    }

}
