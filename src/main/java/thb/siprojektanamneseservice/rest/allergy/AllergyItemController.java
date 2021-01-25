package thb.siprojektanamneseservice.rest.allergy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import thb.siprojektanamneseservice.model.Allergy;
import thb.siprojektanamneseservice.rest.ApiConstants;
import thb.siprojektanamneseservice.service.AllergyService;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@RestController
@CrossOrigin(origins = ApiConstants.CROSS_ORIGIN_PATH)
@RequestMapping(ApiConstants.ALLERGY_TYPE_ITEM)
public class AllergyItemController {

    private static final Logger log = LoggerFactory.getLogger(AllergyItemController.class);
    private final AllergyService allergyService;

    @Autowired
    public AllergyItemController(AllergyService allergyService) {
        this.allergyService = allergyService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Allergy getOne(@PathVariable("allergyTypeId") UUID allergyTypeId){
        log.info("Get the allergyType [id={}]", allergyTypeId);
        Allergy allergy = allergyService.getOne(allergyTypeId);
        log.info("AllergyType with [id={}] fetched", allergyTypeId);

        return allergy;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(@PathVariable("allergyTypeId") UUID allergyTypeId) {
        log.info("Delete the allergyType [id={}]", allergyTypeId);
        allergyService.delete(allergyTypeId);
        log.info("AllergyType with  [id={}] deleted", allergyTypeId);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathVariable("allergyTypeId") UUID allergyTypeId, @RequestBody Allergy allergy) {
        log.info("Update the allergyType [id={}]", allergyTypeId);
        allergyService.update(allergyTypeId, allergy);
        log.info("AllergyType with [id={}] updated", allergyTypeId);
    }
}
