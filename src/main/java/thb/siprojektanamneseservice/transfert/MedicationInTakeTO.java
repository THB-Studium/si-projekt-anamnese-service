package thb.siprojektanamneseservice.transfert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicationInTakeTO {

    @NotNull(message = "The patient id must not be null")
    private UUID patientId;
    private String designation;
    private String dosage;
    private Date startDate;
    private boolean bloodDiluent;
}
