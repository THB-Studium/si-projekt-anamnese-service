package thb.siprojektanamneseservice.rest.address;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import thb.siprojektanamneseservice.model.Address;
import thb.siprojektanamneseservice.rest.ApiConstants;
import thb.siprojektanamneseservice.service.AddressService;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@CrossOrigin(origins = ApiConstants.CROSS_ORIGIN_PATH)
@RequestMapping(ApiConstants.ADDRESS_ROOT)
@Validated
public class AddressRootController {

    private static final Logger log = LoggerFactory.getLogger(AddressRootController.class);

    private final AddressService addressService;

    @Autowired
    public AddressRootController(AddressService addressService) {
        this.addressService = addressService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Address create(@RequestBody @Valid Address newAddress){
        log.info("create an address");
        Address created = addressService.create(newAddress);
        log.info("Address created");

        return created;
    }

    @RequestMapping(method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Address> listAll(){
        log.info("List all addresses");
        List<Address> addresses = addressService.listAll();
        log.info("Addresses list fetched");

        return addresses;
    }
}
