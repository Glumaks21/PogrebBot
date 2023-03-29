package ua.glumaks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.glumaks.domain.BinaryContent;

public interface BinaryContentRepo extends JpaRepository<BinaryContent, Long> {
}
