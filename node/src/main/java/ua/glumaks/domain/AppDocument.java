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
public class AppDocument {

    @Id
    @SequenceGenerator(
            name = "app_document_id_sequence",
            sequenceName = "app_document_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "app_document_id_sequence"
    )
    private Long id;

    private String telegramFieldId;

    private String docName;

    @OneToOne(cascade = CascadeType.ALL)
    private BinaryContent binaryContent;

    private String mimeType;

    private Long fileSize;


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
