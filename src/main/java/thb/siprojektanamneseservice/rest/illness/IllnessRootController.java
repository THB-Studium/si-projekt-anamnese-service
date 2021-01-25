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
import java.util.List;

@RestController
@CrossOrigin(origins = ApiConstants.CROSS_ORIGIN_PATH)
@RequestMapping(ApiConstants.PRE_EXISTING_ILLNESS_ROOT)
@Validated
public class IllnessRootController {

    private static final Logger log = LoggerFactory.getLogger(IllnessRootController.class);
    private final IllnessService illnessService;

    @Autowired
    public IllnessRootController(IllnessService illnessService) {
        this.illnessService = illnessService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Illness create(@RequestBody @Valid Illness newIllness){
        log.info("create a preExistingIllness");
        Illness created = illnessService.create(newIllness);
        log.info("PreExistingIllness created");

        return created;
    }

    @RequestMapping(method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Illness> listAll(){
        log.info("List all preExistingIllnesses");
        List<Illness> illnesses = illnessService.listAll();
        log.info("PreExistingIllnesses list fetched");

        return illnesses;
    }
}
