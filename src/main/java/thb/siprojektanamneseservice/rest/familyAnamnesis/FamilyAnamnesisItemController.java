package thb.siprojektanamneseservice.rest.familyAnamnesis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import thb.siprojektanamneseservice.model.FamilyAnamnesis;
import thb.siprojektanamneseservice.rest.ApiConstants;
import thb.siprojektanamneseservice.service.FamilyAnamnesisService;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@RestController
@CrossOrigin(origins = ApiConstants.CROSS_ORIGIN_PATH)
@RequestMapping(ApiConstants.FAMILYANAMNESIS_ITEM)
@Validated
public class FamilyAnamnesisItemController {

    private static final Logger log = LoggerFactory.getLogger(FamilyAnamnesisItemController.class);
    private final FamilyAnamnesisService familyAnamnesisService;

    @Autowired
    public FamilyAnamnesisItemController(FamilyAnamnesisService familyAnamnesisService) {
        this.familyAnamnesisService = familyAnamnesisService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public FamilyAnamnesis getOne(@PathVariable("familyAnamnesisId") UUID familyAnamnesisId){
        log.info("Get a family anamnesis [id={}]", familyAnamnesisId);
        FamilyAnamnesis familyAnamnesis = familyAnamnesisService.getOne(familyAnamnesisId);
        log.info("Family anamnesis with [id={}] fetched", familyAnamnesisId);

        return familyAnamnesis;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(@PathVariable("familyAnamnesisId") UUID familyAnamnesisId) {
        log.info("Delete a family anamnesis [id={}]", familyAnamnesisId);
        familyAnamnesisService.delete(familyAnamnesisId);
        log.info("Family anamnesis with  [id={}] deleted", familyAnamnesisId);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathVariable("familyAnamnesisId") UUID familyAnamnesisId, @RequestBody @Valid FamilyAnamnesis familyAnamnesis) {
        log.info("Update a family anamnesis [id={}]", familyAnamnesisId);
        familyAnamnesisService.update(familyAnamnesisId, familyAnamnesis);
        log.info("Family anamnesis with [id={}] updated", familyAnamnesisId);
    }
}
