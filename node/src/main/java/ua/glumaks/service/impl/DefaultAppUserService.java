package ua.glumaks.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import ua.glumaks.domain.AppUser;
import ua.glumaks.repostiroty.AppUserRepo;
import ua.glumaks.service.AppUserService;


import static ua.glumaks.domain.UserState.BASIC_STATE;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultAppUserService implements AppUserService {

    private final AppUserRepo repo;


    @Override
    public AppUser findOrSaveAppUser(User telegramUser) {
        var userCandidate = repo.findByTelegramUserId(telegramUser.getId());

        if (userCandidate.isEmpty()) {
            AppUser user = createAppUserFrom(telegramUser);
            return repo.save(user);
        }

        return userCandidate.get();
    }

    private AppUser createAppUserFrom(User telegramUser) {
        return AppUser.builder()
                .telegramUserId(telegramUser.getId())
                .username(telegramUser.getUserName())
                .firstName(telegramUser.getFirstName())
                .lastName(telegramUser.getLastName())
                //TODO change default value after registration
                .isActive(true)
                .state(BASIC_STATE)
                .build();
    }
}
