package ua.glumaks.service.impl;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ua.glumaks.config.EmailConfig;
import ua.glumaks.dto.MailParams;
import ua.glumaks.service.MailService;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender sender;
    private final EmailConfig config;
    private final Validator validator;

    @Override
    public void send(MailParams mailParams) {
        Set<ConstraintViolation<MailParams>> violations = validator.validate(mailParams);
        if (!violations.isEmpty()) {
            log.warn("Incorrect params: " + violations);
            throw new ValidationException("Incorrect params: " + violations.toString());
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(config.getFrom());
        message.setTo(mailParams.getEmailTo());
        message.setSubject(mailParams.getSubject());
        message.setText(mailParams.getText());

        sender.send(message);
    }

}
