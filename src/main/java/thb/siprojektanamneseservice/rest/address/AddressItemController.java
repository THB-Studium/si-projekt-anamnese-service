package thb.siprojektanamneseservice.rest.address;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import thb.siprojektanamneseservice.model.Address;
import thb.siprojektanamneseservice.rest.ApiConstants;
import thb.siprojektanamneseservice.service.AddressService;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@RestController
@CrossOrigin(origins = ApiConstants.CROSS_ORIGIN_PATH)
@RequestMapping(ApiConstants.ADDRESS_ITEM)
public class AddressItemController {

    private static final Logger log = LoggerFactory.getLogger(AddressItemController.class);
    private final AddressService addressService;

    @Autowired
    public AddressItemController(AddressService addressService) {
        this.addressService = addressService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Address getOne(@PathVariable("addressId") UUID addressId){
        log.info("Get the address [id={}]", addressId);
        Address address = addressService.getOne(addressId);
        log.info("Address with [id={}] fetched", addressId);

        return address;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(@PathVariable("addressId") UUID addressId) {
        log.info("Delete the address [id={}]", addressId);
        addressService.delete(addressId);
        log.info("Address with  [id={}] deleted", addressId);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathVariable("addressId") UUID addressId, @RequestBody Address address) {
        log.info("Update the address [id={}]", addressId);
        addressService.update(addressId, address);
        log.info("Address with [id={}] updated", addressId);
    }
}
