package thb.siprojektanamneseservice.rest.person.disease;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import thb.siprojektanamneseservice.model.Diagnosis;
import thb.siprojektanamneseservice.model.Disease;
import thb.siprojektanamneseservice.rest.ApiConstants;
import thb.siprojektanamneseservice.service.DiseaseService;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = ApiConstants.CROSS_ORIGIN_PATH)
@RequestMapping(value = ApiConstants.PERSON_DISEASE_ROOT)
@Validated
public class PersonDiseaseRootController {

    private static final Logger log = LoggerFactory.getLogger(PersonDiseaseRootController.class);
    private final DiseaseService diseaseService;

    @Autowired
    public PersonDiseaseRootController(DiseaseService diseaseService) {
        this.diseaseService = diseaseService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Disease> listAll(@PathVariable("personId") UUID personId){
        log.info("Fetch diseases by personId [id={}]", personId);
        List<Disease> diseases = diseaseService.listAllByPersonId(personId);
        log.info("Diseases by personId [id={}] fetched", personId);

        return diseases;
    }
}
