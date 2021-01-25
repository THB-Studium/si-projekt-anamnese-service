package thb.siprojektanamneseservice.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class VegetativeAnamnesis implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @NotNull(message = "The date muss not be null")
    @NotEmpty(message = "The date muss not be empty")
    private Date date;

    @JoinColumn(name = "person_Id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Person person;

    private boolean insomnia;
    private boolean sleepDisorders;

    //VegetativeAnamnesisDecisionValues
    private String thirst;
    private String appetite;
    private String bowelMovement;
    private String urination;
}
