package pjatk.socialeventorganizer.SocialEventOrganizer.model.dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Table("address")
public class Address implements Serializable {

    @Id
    @Column("id_address")
    Long id;

    String country;

    String city;

    @Column("street_name")
    String streetName;

    @Column("street_number")
    int streetNumber;

    @Column("zip_code")
    String zipCode;
}
