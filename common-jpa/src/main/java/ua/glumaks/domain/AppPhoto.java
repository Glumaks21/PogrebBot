package ua.glumaks.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class AppPhoto {

    @Id
    @SequenceGenerator(

            name = "app_photo_id_sequence",
            sequenceName = "app_photo_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "app_photo_id_sequence"
    )
    private Long id;
    private String telegramFieldId;

    @OneToOne(cascade = CascadeType.ALL)
    private BinaryContent binaryContent;

    private Integer fileSize;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AppDocument that = (AppDocument) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
