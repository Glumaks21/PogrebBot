package ua.glumaks.repostiroty;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.glumaks.entity.RawData;

public interface RawDataRepository extends JpaRepository<RawData, Long> {
}
