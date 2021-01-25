package thb.siprojektanamneseservice.rest.diagnosis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import thb.siprojektanamneseservice.model.Diagnosis;
import thb.siprojektanamneseservice.rest.ApiConstants;
import thb.siprojektanamneseservice.service.DiagnosisService;
import thb.siprojektanamneseservice.transfert.DiagnosisTO;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@CrossOrigin(origins = ApiConstants.CROSS_ORIGIN_PATH)
@RequestMapping(ApiConstants.DIAGNOSIS_ROOT)
@Validated
public class DiagnosisRootController {

    private static final Logger log = LoggerFactory.getLogger(DiagnosisRootController.class);

    private final DiagnosisService diagnosisService;

    @Autowired
    public DiagnosisRootController(DiagnosisService diagnosisService) {
        this.diagnosisService = diagnosisService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Diagnosis create(@RequestBody @Valid DiagnosisTO newDiagnosisTO){
        log.info("create a diagnosis");
        Diagnosis created = diagnosisService.create(newDiagnosisTO);
        log.info("Diagnosis created");

        return created;
    }

    @RequestMapping(method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Diagnosis> listAll(){
        log.info("List all diagnoses");
        List<Diagnosis> diagnoses = diagnosisService.listAll();
        log.info("Diagnoses list fetched");

        return diagnoses;
    }
}
