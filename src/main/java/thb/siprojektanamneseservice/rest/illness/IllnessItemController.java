package thb.siprojektanamneseservice.rest.illness;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import thb.siprojektanamneseservice.model.Illness;
import thb.siprojektanamneseservice.rest.ApiConstants;
import thb.siprojektanamneseservice.service.IllnessService;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@RestController
@CrossOrigin(origins = ApiConstants.CROSS_ORIGIN_PATH)
@RequestMapping(ApiConstants.PRE_EXISTING_ILLNESS_ITEM)
@Validated
public class IllnessItemController {

    private static final Logger log = LoggerFactory.getLogger(IllnessItemController.class);
    private final IllnessService illnessService;

    @Autowired
    public IllnessItemController(IllnessService illnessService) {
        this.illnessService = illnessService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Illness getOne(@PathVariable("preExistingIllnessId") UUID preExistingIllnessId){
        log.info("Get the preExistingIllness [id={}]", preExistingIllnessId);
        Illness illness = illnessService.getOne(preExistingIllnessId);
        log.info("PreExistingIllness with [id={}] fetched", preExistingIllnessId);

        return illness;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(@PathVariable("preExistingIllnessId") UUID preExistingIllnessId) {
        log.info("Delete the preExistingIllness [id={}]", preExistingIllnessId);
        illnessService.delete(preExistingIllnessId);
        log.info("PreExistingIllness with  [id={}] deleted", preExistingIllnessId);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathVariable("preExistingIllnessId") UUID preExistingIllnessId, @RequestBody @Valid Illness illness) {
        log.info("Update the preExistingIllness [id={}]", preExistingIllnessId);
        illnessService.update(preExistingIllnessId, illness);
        log.info("PreExistingIllness with [id={}] updated", preExistingIllnessId);
    }
}
