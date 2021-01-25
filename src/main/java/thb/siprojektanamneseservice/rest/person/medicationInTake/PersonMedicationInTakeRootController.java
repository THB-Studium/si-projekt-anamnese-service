package thb.siprojektanamneseservice.rest.person.medicationInTake;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import thb.siprojektanamneseservice.model.MedicationInTake;
import thb.siprojektanamneseservice.rest.ApiConstants;
import thb.siprojektanamneseservice.service.MedicationInTakeService;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = ApiConstants.CROSS_ORIGIN_PATH)
@RequestMapping(value = ApiConstants.PERSON_MEDICATION_IN_TAKE_ROOT)
@Validated
public class PersonMedicationInTakeRootController {

    private static final Logger log = LoggerFactory.getLogger(PersonMedicationInTakeRootController.class);
    private final MedicationInTakeService medicationInTakeService;

    @Autowired
    public PersonMedicationInTakeRootController(MedicationInTakeService medicationInTakeService) {
        this.medicationInTakeService = medicationInTakeService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public List<MedicationInTake> listAll(@PathVariable("personId") UUID personId) {
        log.info("Fetch medicationInTakes by personId [id={}]", personId);
        List<MedicationInTake> medicationInTakes = medicationInTakeService.listAllByPersonId(personId);
        log.info("MedicationInTakes by personId [id={}] fetched", personId);

        return medicationInTakes;
    }
}
