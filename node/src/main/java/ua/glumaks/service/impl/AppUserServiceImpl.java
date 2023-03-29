package ua.glumaks.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.User;
import ua.glumaks.domain.AppUser;
import ua.glumaks.dto.MailParams;
import ua.glumaks.exceptions.EmailServiceException;
import ua.glumaks.repository.AppUserRepo;
import ua.glumaks.service.AppUserService;

import java.util.Optional;
import java.util.UUID;

import static ua.glumaks.domain.UserState.BASIC_STATE;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepo repo;
    private final RestTemplate restTemplate;

    @Value("${service.rest-service.url}")
    private String restServiceUrl;
    @Value("${service.mail.url}")
    private String mailServiceUrl;


    @Override
    public void register(AppUser user, String email) {
        if (user.getActivationCode() != null) {
            throw new IllegalStateException("User is already registered, need to activate an account");
        }

        EmailValidator emailValidator = EmailValidator.getInstance();
        if (!emailValidator.isValid(email)) {
            throw new IllegalArgumentException("Email is not correct: " + email);
        }

        if (repo.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already registered");
        }

        user.setEmail(email);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setState(BASIC_STATE);
        repo.save(user);

        try {
            ResponseEntity<String> response = sendActivationCode(user);
            if (!response.getStatusCode().is2xxSuccessful()) {
                user.setEmail(null);
                user.setActivationCode(null);
                repo.save(user);
                throw new EmailServiceException("Bad response: " + response);
            }
        } catch (ResourceAccessException e) {
            user.setEmail(null);
            user.setActivationCode(null);
            repo.save(user);
            throw new EmailServiceException("Can't access a resource, server may not be running", e);
        }
    }

    private ResponseEntity<String> sendActivationCode(AppUser user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        MailParams mailParams = createActivationMailParams(user);

        HttpEntity<MailParams> request = new HttpEntity<>(mailParams, headers);
        String uri = String.format("%s/send", mailServiceUrl);

        return restTemplate.postForEntity(uri, request, String.class);
    }

    private MailParams createActivationMailParams(AppUser user) {
        String subject = "Activation of account";
        String link = String.format("%s/users/activate?id=%s",
                restServiceUrl, user.getActivationCode());
        String text = "To activate your account follow the link: " + link;
        return MailParams.builder()
                .emailTo(user.getEmail())
                .subject(subject)
                .text(text)
                .build();
    }

    @Override
    public AppUser findOrSaveAppUser(User telegramUser) {
        Optional<AppUser> userCandidate =
                repo.findByTelegramUserId(telegramUser.getId());

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
                .state(BASIC_STATE)
                .build();
    }

}
