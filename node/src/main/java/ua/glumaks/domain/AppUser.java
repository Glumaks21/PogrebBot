package ua.glumaks.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import ua.glumaks.domain.UserState;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class AppUser {

    @Id
    @SequenceGenerator(
            name = "app_user_id_sequence",
            sequenceName = "app_user_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "app_user_id_sequence"
    )
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long telegramUserId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime firstLoginData;

    private String firstName;
    private String lastName;
    private String username;

    @Email(message = "Incorrect email")
    private String email;
    private String activationCode;

    @Enumerated(EnumType.STRING)
    private UserState state;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ua.glumaks.domain.AppUser appUser = (ua.glumaks.domain.AppUser) o;
        return getId() != null && Objects.equals(getId(), appUser.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}