package ua.glumaks.service;

import org.telegram.telegrambots.meta.api.objects.User;
import ua.glumaks.domain.AppUser;

import java.util.Optional;

public interface AppUserService {
    AppUser findOrSaveAppUser(User user);
}
