package ua.glumaks.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MailParams {

    private String emailTo;

    private String subject;

    private String text;

}
