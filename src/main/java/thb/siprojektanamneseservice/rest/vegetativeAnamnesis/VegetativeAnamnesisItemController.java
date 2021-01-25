package thb.siprojektanamneseservice.rest.vegetativeAnamnesis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import thb.siprojektanamneseservice.model.VegetativeAnamnesis;
import thb.siprojektanamneseservice.rest.ApiConstants;
import thb.siprojektanamneseservice.service.VegetativeAnamnesisService;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@RestController
@CrossOrigin(origins = ApiConstants.CROSS_ORIGIN_PATH)
@RequestMapping(ApiConstants.VEGETATIVEANAMNESIS_ITEM)
@Validated
public class VegetativeAnamnesisItemController {

    private static final Logger log = LoggerFactory.getLogger(VegetativeAnamnesisItemController.class);
    private final VegetativeAnamnesisService vegetativeAnamnesisService;

    @Autowired
    public VegetativeAnamnesisItemController(VegetativeAnamnesisService vegetativeAnamnesisService) {
        this.vegetativeAnamnesisService = vegetativeAnamnesisService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public VegetativeAnamnesis getOne(@PathVariable("vegetativeAnamnesisId") UUID vegetativeAnamnesisId){
        log.info("Get a vegetative anamnesis [id={}]", vegetativeAnamnesisId);
        VegetativeAnamnesis vegetativeAnamnesis = vegetativeAnamnesisService.getOne(vegetativeAnamnesisId);
        log.info("Vegetative anamnesis with [id={}] fetched", vegetativeAnamnesisId);

        return vegetativeAnamnesis;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(@PathVariable("vegetativeAnamnesisId") UUID vegetativeAnamnesisId) {
        log.info("Delete a vegetative anamnesis [id={}]", vegetativeAnamnesisId);
        vegetativeAnamnesisService.delete(vegetativeAnamnesisId);
        log.info("Vegetative anamnesis with  [id={}] deleted", vegetativeAnamnesisId);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathVariable("vegetativeAnamnesisId") UUID vegetativeAnamnesisId, @RequestBody @Valid VegetativeAnamnesis vegetativeAnamnesis) {
        log.info("Update a vegetative anamnesis [id={}]", vegetativeAnamnesisId);
        vegetativeAnamnesisService.update(vegetativeAnamnesisId, vegetativeAnamnesis);
        log.info("Vegetative anamnesis with [id={}] updated", vegetativeAnamnesisId);
    }
}
