package thb.siprojektanamneseservice.rest.disease;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import thb.siprojektanamneseservice.model.Disease;
import thb.siprojektanamneseservice.rest.ApiConstants;
import thb.siprojektanamneseservice.service.DiseaseService;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@RestController
@CrossOrigin(origins = ApiConstants.CROSS_ORIGIN_PATH)
@RequestMapping(ApiConstants.DISEASE_ITEM)
@Validated
public class DiseaseItemController {

    private static final Logger log = LoggerFactory.getLogger(DiseaseItemController.class);
    private final DiseaseService diseaseService;

    @Autowired
    public DiseaseItemController(DiseaseService diseaseService) {
        this.diseaseService = diseaseService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Disease getOne(@PathVariable("diseaseId") UUID diseaseId){
        log.info("Get the disease [id={}]", diseaseId);
        Disease disease = diseaseService.getOne(diseaseId);
        log.info("Disease with [id={}] fetched", diseaseId);

        return disease;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(@PathVariable("diseaseId") UUID diseaseId) {
        log.info("Delete the disease [id={}]", diseaseId);
        diseaseService.delete(diseaseId);
        log.info("Disease with  [id={}] deleted", diseaseId);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathVariable("diseaseId") UUID diseaseId, @RequestBody @Valid Disease disease) {
        log.info("Update the disease [id={}]", diseaseId);
        diseaseService.update(diseaseId, disease);
        log.info("Disease with [id={}] updated", diseaseId);
    }
}
