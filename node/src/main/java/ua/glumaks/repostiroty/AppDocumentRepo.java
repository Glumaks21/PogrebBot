package ua.glumaks.repostiroty;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.glumaks.domain.AppDocument;

public interface AppDocumentRepo extends JpaRepository<AppDocument, Long> {
}
