package thb.siprojektanamneseservice.rest.person;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import thb.siprojektanamneseservice.model.Person;
import thb.siprojektanamneseservice.rest.ApiConstants;
import thb.siprojektanamneseservice.service.PersonService;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@RestController
@CrossOrigin(origins = ApiConstants.CROSS_ORIGIN_PATH)
@RequestMapping(value = ApiConstants.PERSON_ITEM)
@Validated
public class PersonItemController {

    private static final Logger log = LoggerFactory.getLogger(PersonItemController.class);
    private final PersonService personService;

    @Autowired
    public PersonItemController(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Person getOne(@PathVariable("personId") UUID personId){
        log.info("Get a person [id={}]", personId);
        Person person = personService.getOne(personId);
        log.info("Person with [id={}] fetched", personId);

        return person;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(@PathVariable("personId") UUID personId) {
        log.info("Delete a person [id={}]", personId);
        personService.delete(personId);
        log.info("Person with  [id={}] deleted", personId);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathVariable("personId") UUID personId, @RequestBody @Valid Person person) {
        log.info("Update a person [id={}]", personId);
        personService.update(personId, person);
        log.info("Person with [id={}] updated", personId);
    }
}
