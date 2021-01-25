package thb.siprojektanamneseservice.transfert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import thb.siprojektanamneseservice.model.Illness;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiseaseTO {

    @NotNull(message = "The patient id must not be null")
    @NotEmpty(message = "The patient id must not be empty")
    private UUID patientId;
    private boolean undergoneSurgery;
    private String surgeriesDetails;
    private List<Illness> preExistingIllnesses = new ArrayList<>();
}
