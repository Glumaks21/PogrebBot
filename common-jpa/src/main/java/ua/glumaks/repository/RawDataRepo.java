package ua.glumaks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.glumaks.domain.RawData;

public interface RawDataRepo extends JpaRepository<RawData, Long> {
}
