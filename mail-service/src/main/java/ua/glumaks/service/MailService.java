package ua.glumaks.service;

import ua.glumaks.dto.MailParams;

public interface MailService {

    void send(MailParams mailParams);

}
