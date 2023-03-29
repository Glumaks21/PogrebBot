package ua.glumaks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.glumaks.domain.AppPhoto;

public interface AppPhotoRepo extends JpaRepository<AppPhoto, Long> {
}
