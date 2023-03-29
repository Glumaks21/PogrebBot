package ua.glumaks.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.glumaks.domain.AppDocument;
import ua.glumaks.domain.AppPhoto;
import ua.glumaks.repository.AppDocumentRepo;
import ua.glumaks.repository.AppPhotoRepo;
import ua.glumaks.service.FileService;
import ua.glumaks.utils.CryptoUtil;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final AppDocumentRepo docRepo;
    private final AppPhotoRepo photoRepo;
    private final CryptoUtil hashUtil;


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


}
