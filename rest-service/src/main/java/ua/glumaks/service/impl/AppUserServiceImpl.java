package ua.glumaks.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.glumaks.domain.AppUser;
import ua.glumaks.repository.AppUserRepo;
import ua.glumaks.service.AppUserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepo userRepo;


    @Override
    public boolean activate(String activationCode) {
        Optional<AppUser> userCandidate = userRepo.findByActivationCode(activationCode);
        if (userCandidate.isPresent()) {
            AppUser user = userCandidate.get();
            user.setActivationCode(null);
            userRepo.save(user);
            return true;
        }

        return false;
    }

}
