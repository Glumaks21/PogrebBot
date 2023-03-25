package ua.glumaks.rpository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.glumaks.domain.BinaryContent;

public interface BinaryContentRepo extends JpaRepository<BinaryContent, Long> {
}
