package thb.siprojektanamneseservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
public class MedicationInTake implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @JoinColumn(name = "person_Id")
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "The person must not be null")
    private Person person;

    @NotNull(message = "The designation muss not be null")
    @NotEmpty(message = "The designation muss not be empty")
    private String designation;
    private String dosage;
    private Date startDate;
    private boolean bloodDiluent;

}
