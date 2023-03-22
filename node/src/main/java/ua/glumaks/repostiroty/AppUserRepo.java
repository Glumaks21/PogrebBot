package ua.glumaks.repostiroty;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.glumaks.domain.AppUser;

import java.util.Optional;

public interface AppUserRepo extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByTelegramUserId(Long telegramId);
}
