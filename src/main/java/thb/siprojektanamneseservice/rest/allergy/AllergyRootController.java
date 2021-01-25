package thb.siprojektanamneseservice.rest.allergy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import thb.siprojektanamneseservice.model.Allergy;
import thb.siprojektanamneseservice.rest.ApiConstants;
import thb.siprojektanamneseservice.service.AllergyService;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@CrossOrigin(origins = ApiConstants.CROSS_ORIGIN_PATH)
@RequestMapping(ApiConstants.ALLERGY_TYPE_ROOT)
@Validated
public class AllergyRootController {

    private static final Logger log = LoggerFactory.getLogger(AllergyRootController.class);
    private final AllergyService allergyService;

    @Autowired
    public AllergyRootController(AllergyService allergyService) {
        this.allergyService = allergyService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Allergy create(@RequestBody @Valid Allergy newAllergy){
        log.info("create an allergyType");
        Allergy created = allergyService.create(newAllergy);
        log.info("AllergyType created");

        return created;
    }

    @RequestMapping(method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Allergy> listAll(){
        log.info("List all allergyTypes");
        List<Allergy> allergies = allergyService.listAll();
        log.info("AllergyTypes list fetched");

        return allergies;
    }
}
