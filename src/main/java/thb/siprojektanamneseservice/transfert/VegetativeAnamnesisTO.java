package thb.siprojektanamneseservice.transfert;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
public class VegetativeAnamnesisTO {

    @NotNull(message = "The patient id must not be null")
    @NotEmpty(message = "The patient id must not be empty")
    private UUID patientId;

    private Date date;
    private boolean insomnia;
    private boolean sleepDisorders;

    //VegetativeAnamnesisDecisionValues
    private String thirst;
    private String appetite;
    private String bowelMovement;
    private String urination;
}
