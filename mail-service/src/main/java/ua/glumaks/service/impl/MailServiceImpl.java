package ua.glumaks.service.impl;

import jakarta.mail.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ua.glumaks.dto.MailParams;
import ua.glumaks.service.MailService;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender sender;
    @Value("${spring.mail.username}")
    private String mailFrom;

    @Value("${service.activate.uri}")
    private String activationUri;


    @Override
    public void send(MailParams mailParams) {
        String subject = "Activation of account";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailFrom);
        message.setTo(mailParams.getEmailTo());
        message.setSubject(subject);
        message.setText(getActivationMailBody(mailParams.getId()));

        sender.send(message);
    }

    private String getActivationMailBody(String id) {
        return ("To activate your account click on the link: " + activationUri).replace("{id}", id);
    }

}
