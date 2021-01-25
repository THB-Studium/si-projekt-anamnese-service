package thb.siprojektanamneseservice.rest.vegetativeAnamnesis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import thb.siprojektanamneseservice.model.VegetativeAnamnesis;
import thb.siprojektanamneseservice.rest.ApiConstants;
import thb.siprojektanamneseservice.service.VegetativeAnamnesisService;
import thb.siprojektanamneseservice.transfert.VegetativeAnamnesisTO;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@CrossOrigin(origins = ApiConstants.CROSS_ORIGIN_PATH)
@RequestMapping(ApiConstants.VEGETATIVEANAMNESIS_ROOT)
@Validated
public class VegetativeAnamnesisRootController {

    private static final Logger log = LoggerFactory.getLogger(VegetativeAnamnesisRootController.class);

    private final VegetativeAnamnesisService vegetativeAnamnesisService;

    @Autowired
    public VegetativeAnamnesisRootController(VegetativeAnamnesisService vegetativeAnamnesisService) {
        this.vegetativeAnamnesisService = vegetativeAnamnesisService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public VegetativeAnamnesis create(@RequestBody @Valid VegetativeAnamnesisTO newVegetativeAnamnesisTO){
        log.info("create a vegetative anamnesis");
        VegetativeAnamnesis created = vegetativeAnamnesisService.create(newVegetativeAnamnesisTO);
        log.info("Vegetative anamnesis created");

        return created;
    }

    @RequestMapping(method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public List<VegetativeAnamnesis> listAll(){
        log.info("List all vegetative anamnesis");
        List<VegetativeAnamnesis> vegetativeAnamnesis = vegetativeAnamnesisService.listAll();
        log.info("Vegetative anamnesis list fetched");

        return vegetativeAnamnesis;
    }
}
