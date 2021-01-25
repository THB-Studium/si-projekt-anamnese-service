package thb.siprojektanamneseservice.rest.diagnosis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import thb.siprojektanamneseservice.model.Diagnosis;
import thb.siprojektanamneseservice.rest.ApiConstants;
import thb.siprojektanamneseservice.service.DiagnosisService;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@RestController
@CrossOrigin(origins = ApiConstants.CROSS_ORIGIN_PATH)
@RequestMapping(ApiConstants.DIAGNOSIS_ITEM)
@Validated
public class DiagnosisItemController {

    private static final Logger log = LoggerFactory.getLogger(DiagnosisItemController.class);
    private final DiagnosisService diagnosisService;

    @Autowired
    public DiagnosisItemController(DiagnosisService diagnosisService) {
        this.diagnosisService = diagnosisService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Diagnosis getOne(@PathVariable("diagnosisId") UUID diagnosisId){
        log.info("Get the diagnosis [id={}]", diagnosisId);
        Diagnosis diagnosis = diagnosisService.getOne(diagnosisId);
        log.info("Diagnosis with [id={}] fetched", diagnosisId);

        return diagnosis;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(@PathVariable("diagnosisId") UUID diagnosisId) {
        log.info("Delete the diagnosis [id={}]", diagnosisId);
        diagnosisService.delete(diagnosisId);
        log.info("Diagnosis with  [id={}] deleted", diagnosisId);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathVariable("diagnosisId") UUID diagnosisId, @RequestBody @Valid Diagnosis diagnosis) {
        log.info("Update a person [id={}]", diagnosisId);
        diagnosisService.update(diagnosisId, diagnosis);
        log.info("Person with [id={}] updated", diagnosisId);
    }
}
