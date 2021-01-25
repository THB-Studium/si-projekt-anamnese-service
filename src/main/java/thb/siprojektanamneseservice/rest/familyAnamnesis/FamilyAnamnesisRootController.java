package thb.siprojektanamneseservice.rest.familyAnamnesis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import thb.siprojektanamneseservice.model.FamilyAnamnesis;
import thb.siprojektanamneseservice.rest.ApiConstants;
import thb.siprojektanamneseservice.service.FamilyAnamnesisService;
import thb.siprojektanamneseservice.transfert.FamilyAnamnesisTO;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@CrossOrigin(origins = ApiConstants.CROSS_ORIGIN_PATH)
@RequestMapping(ApiConstants.FAMILYANAMNESIS_ROOT)
@Validated
public class FamilyAnamnesisRootController {

    private static final Logger log = LoggerFactory.getLogger(FamilyAnamnesisRootController.class);

    private final FamilyAnamnesisService familyAnamnesisService;

    @Autowired
    public FamilyAnamnesisRootController(FamilyAnamnesisService familyAnamnesisService) {
        this.familyAnamnesisService = familyAnamnesisService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public FamilyAnamnesis create(@RequestBody @Valid FamilyAnamnesisTO newFamilyAnamnesisTO){
        log.info("create a family anamnesis");
        FamilyAnamnesis created = familyAnamnesisService.create(newFamilyAnamnesisTO);
        log.info("Family anamnesis created");

        return created;
    }

    @RequestMapping(method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public List<FamilyAnamnesis> listAll(){
        log.info("List all family anamnesis");
        List<FamilyAnamnesis> familyAnamnesis = familyAnamnesisService.listAll();
        log.info("Family anamnesis list fetched");

        return familyAnamnesis;
    }
}
