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
    ResponseEntity<FileSystemResource> getDocument(@RequestParam("id") String id) {
        //TODO add controller advice
        AppDocument document = fileService.getDocument(id);
        if (document == null) {
            throw new IllegalArgumentException("id: " + id);
        }

        BinaryContent binaryContent = document.getBinaryContent();
        FileSystemResource systemResource = fileService.getFileSystemResource(binaryContent);
        if (fileService == null) {
            throw new IllegalStateException("Failure to load file");
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(document.getMimeType()))
                .header("Content-disposition", "attachment; filename=" + document.getDocName())
                .body(systemResource);
    }

    @GetMapping("/photo")
    ResponseEntity<FileSystemResource> getPhoto(@RequestParam("id") String id) {
        //TODO add controller advice
        AppPhoto photo = fileService.getPhoto(id);
        if (photo == null) {
            throw new IllegalArgumentException("id: " + id);
        }

        BinaryContent binaryContent = photo.getBinaryContent();
        FileSystemResource systemResource = fileService.getFileSystemResource(binaryContent);
        if (fileService == null) {
            throw new IllegalStateException("Failure to load file");
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header("Content-disposition", "attachment;")
                .body(systemResource);
    }

}
