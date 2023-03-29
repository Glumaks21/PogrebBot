package ua.glumaks.service;

import org.telegram.telegrambots.meta.api.objects.User;
import ua.glumaks.domain.AppUser;


public interface AppUserService {

    void register(AppUser appUser, String email);
    AppUser findOrSaveAppUser(User user);

}
