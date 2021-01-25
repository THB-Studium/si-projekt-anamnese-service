package thb.siprojektanamneseservice.rest.person.diagnosis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import thb.siprojektanamneseservice.model.Diagnosis;
import thb.siprojektanamneseservice.model.Person;
import thb.siprojektanamneseservice.rest.ApiConstants;
import thb.siprojektanamneseservice.service.DiagnosisService;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = ApiConstants.CROSS_ORIGIN_PATH)
@RequestMapping(value = ApiConstants.PERSON_DIAGNOSIS_ROOT)
@Validated
public class PersonDiagnosisRootController {

    private static final Logger log = LoggerFactory.getLogger(PersonDiagnosisRootController.class);
    private final DiagnosisService diagnosisService;

    @Autowired
    public PersonDiagnosisRootController(DiagnosisService diagnosisService) {
        this.diagnosisService = diagnosisService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Diagnosis> listAll(@PathVariable("personId") UUID personId){
        log.info("Fetch diagnosis by personId [id={}]", personId);
        List<Diagnosis> diagnosis = diagnosisService.listAllByPersonId(personId);
        log.info("Diagnosis by personId [id={}] fetched", personId);

        return diagnosis;
    }
}
