package ua.glumaks.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailParams {

    @Email
    private String emailTo;

    @NotEmpty
    private String subject;

    @NotEmpty
    private String text;

}
