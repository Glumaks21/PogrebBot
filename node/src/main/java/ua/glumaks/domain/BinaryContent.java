package ua.glumaks.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Entity
public class BinaryContent {

    @Id
    @SequenceGenerator(
            name = "binary_content_id_sequence",
            sequenceName = "binary_content_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "binary_content_id_sequence"
    )
    private Long id;

    private byte[] fileAsArrayOfBytes;

}
