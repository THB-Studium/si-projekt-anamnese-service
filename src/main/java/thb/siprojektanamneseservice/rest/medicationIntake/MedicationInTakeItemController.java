package thb.siprojektanamneseservice.rest.medicationIntake;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import thb.siprojektanamneseservice.model.MedicationInTake;
import thb.siprojektanamneseservice.rest.ApiConstants;
import thb.siprojektanamneseservice.service.MedicationInTakeService;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@RestController
@CrossOrigin(origins = ApiConstants.CROSS_ORIGIN_PATH)
@RequestMapping(ApiConstants.MEDICATION_IN_TAKE_ITEM)
@Validated
public class MedicationInTakeItemController {

    private static final Logger log = LoggerFactory.getLogger(MedicationInTakeItemController.class);
    private final MedicationInTakeService medicationInTakeService;

    @Autowired
    public MedicationInTakeItemController(MedicationInTakeService medicationInTakeService) {
        this.medicationInTakeService = medicationInTakeService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public MedicationInTake getOne(@PathVariable("medicationInTakeId") UUID medicationInTakeId){
        log.info("Get a medicationInTake [id={}]", medicationInTakeId);
        MedicationInTake medicationInTake = medicationInTakeService.getOne(medicationInTakeId);
        log.info("MedicationIntake with [id={}] fetched", medicationInTakeId);

        return medicationInTake;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(@PathVariable("medicationInTakeId") UUID medicationInTakeId) {
        log.info("Delete the medicationInTake [id={}]", medicationInTakeId);
        medicationInTakeService.delete(medicationInTakeId);
        log.info("MedicationInTake with  [id={}] deleted", medicationInTakeId);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathVariable("medicationInTakeId") UUID medicationInTakeId, @RequestBody @Valid MedicationInTake medicationInTake) {
        log.info("Update the medicationInTake [id={}]", medicationInTakeId);
        medicationInTakeService.update(medicationInTakeId, medicationInTake);
        log.info("MedicationInTake with [id={}] updated", medicationInTakeId);
    }
}
