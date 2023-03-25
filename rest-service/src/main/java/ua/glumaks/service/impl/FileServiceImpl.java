package ua.glumaks.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import ua.glumaks.domain.AppDocument;
import ua.glumaks.domain.AppPhoto;
import ua.glumaks.domain.BinaryContent;
import ua.glumaks.rpository.AppDocumentRepo;
import ua.glumaks.rpository.AppPhotoRepo;
import ua.glumaks.service.FileService;
import ua.glumaks.utils.HashUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final AppDocumentRepo docRepo;
    private final AppPhotoRepo photoRepo;
    private final HashUtil hashUtil;

    @Override
    public AppDocument getDocument(String docId) {
        Long id = Long.parseLong(hashUtil.decode(docId));
        return docRepo.findById(id).orElse(null);
    }

    @Override
    public AppPhoto getPhoto(String photoId) {
        Long id = Long.parseLong(hashUtil.decode(photoId));
        return photoRepo.findById(id).orElse(null);
    }

    @Override
    public FileSystemResource getFileSystemResource(BinaryContent binaryContent) {
        try {
            File temp = File.createTempFile("temp", ".bin");
            temp.deleteOnExit();
            Files.write(temp.toPath(), binaryContent.getFileAsArrayOfBytes());
            return new FileSystemResource(temp);
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
            return null;
        }
    }

}
