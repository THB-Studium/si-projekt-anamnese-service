package thb.siprojektanamneseservice.transfert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosisTO {

    private String examinationName;
    private Date examinationDate;
    private String bodyRegion;

    private UUID personId;
}
