package ua.glumaks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.glumaks.entity.AppUser;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByTelegramUserId(Long telegramId);
}
