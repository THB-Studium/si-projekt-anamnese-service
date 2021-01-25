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
import java.util.List;

@RestController
@CrossOrigin(origins = ApiConstants.CROSS_ORIGIN_PATH)
@RequestMapping(ApiConstants.SECURITY_ROOT)
@Validated
public class SecurityRootController {

    private static final Logger log = LoggerFactory.getLogger(SecurityRootController.class);

    private final SecurityService securityService;

    @Autowired
    public SecurityRootController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Security create(@RequestBody @Valid Security newSecurity){
        log.info("create an security");
        Security created = securityService.create(newSecurity);
        log.info("Security created");

        return created;
    }

    @RequestMapping(method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Security> listAll(){
        log.info("List all securities");
        List<Security> securities = securityService.listAll();
        log.info("Securities list fetched");

        return securities;
    }
}
