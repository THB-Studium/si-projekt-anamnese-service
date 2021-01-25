package thb.siprojektanamneseservice.rest.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import thb.siprojektanamneseservice.model.Security;
import thb.siprojektanamneseservice.rest.ApiConstants;
import thb.siprojektanamneseservice.service.SecurityService;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@RestController
@CrossOrigin(origins = ApiConstants.CROSS_ORIGIN_PATH)
@RequestMapping(ApiConstants.SECURITY_ITEM)
@Validated
public class SecurityItemController {

    private static final Logger log = LoggerFactory.getLogger(SecurityItemController.class);
    private final SecurityService securityService;

    @Autowired
    public SecurityItemController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Security getOne(@PathVariable("securityId") UUID securityId){
        log.info("Get the security [id={}]", securityId);
        Security security = securityService.getOne(securityId);
        log.info("Security with [id={}] fetched", securityId);

        return security;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(@PathVariable("securityId") UUID securityId) {
        log.info("Delete the security [id={}]", securityId);
        securityService.delete(securityId);
        log.info("Security with  [id={}] deleted", securityId);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathVariable("securityId") @Valid UUID securityId, @RequestBody Security security) {
        log.info("Update the security [id={}]", securityId);
        securityService.update(securityId, security);
        log.info("Security with [id={}] updated", securityId);
    }
}
