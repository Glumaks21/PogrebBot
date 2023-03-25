package ua.glumaks.service;

import jakarta.mail.Message;
import ua.glumaks.dto.MailParams;

public interface MailService {
    void send(MailParams mailParams);
}
