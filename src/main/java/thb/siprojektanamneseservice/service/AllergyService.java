package thb.siprojektanamneseservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thb.siprojektanamneseservice.exceptions.ResourceBadRequestException;
import thb.siprojektanamneseservice.exceptions.ResourceNotFoundException;
import thb.siprojektanamneseservice.model.Allergy;
import thb.siprojektanamneseservice.model.Person;
import thb.siprojektanamneseservice.repository.AllergyRepository;
import thb.siprojektanamneseservice.repository.PersonRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("AllergyTypeService")
@Transactional(rollbackOn = Exception.class)
public class AllergyService {

    private final AllergyRepository repository;
    private final PersonRepository personRepository;
    private final PersonService personService;

    @Autowired
    public AllergyService(AllergyRepository repository, PersonRepository personRepository, PersonService personService){
        this.repository = repository;
        this.personRepository = personRepository;
        this.personService = personService;
    }

    public List<Allergy> listAll() {
        return  repository.findAll();
    }

    public Allergy getOne(UUID allergyTypeId) throws ResourceNotFoundException {
        Optional<Allergy> allergyOP = repository.findById(allergyTypeId);
        if (!allergyOP.isPresent()) {
            throw new ResourceNotFoundException(
                    String.format("The allergyType with the id %s does not exist", allergyTypeId.toString())
            );
        }
        return allergyOP.get();
    }

    public void delete(UUID allergyType) {
        getOne(allergyType);
        repository.deleteById(allergyType);
    }

    public void deleteByPersonId(UUID personId, UUID allergyTypeId) {
        getOne(allergyTypeId);
        Person personFound = personService.getOne(personId);
        List<Allergy> personAllergies = new ArrayList<>();

        personFound.getAllergies().forEach(allergy -> {
            if (!allergy.getId().equals(allergyTypeId)) {
                personAllergies.add(allergy);
            }
        });

        personFound.setAllergies(personAllergies);

        personRepository.save(personFound);
    }

    /**
     * @param newAllergy to be created
     * @return The new created allergyType
     */
    public Allergy create(Allergy newAllergy) {
        checkForUniqueness(newAllergy);
        newAllergy.setId(null);
        return repository.save(newAllergy);
    }


    public Allergy update(UUID allergyTypeId, Allergy update) throws ResourceNotFoundException {
        Allergy allergyFound = getOne(allergyTypeId);

        if (!allergyFound.getId().equals(update.getId())){
            checkForUniqueness(update);
        }
        update.setId(allergyTypeId);
        return repository.save(update);
    }

    private void checkForUniqueness(Allergy allergy) {
        if (repository.countById(allergy.getId()) > 0){
            throw new ResourceBadRequestException(
                    String.format("A allergyType with the id %s already exist", allergy.getId())
            );
        }
    }
}
