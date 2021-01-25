package thb.siprojektanamneseservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thb.siprojektanamneseservice.exceptions.ResourceBadRequestException;
import thb.siprojektanamneseservice.exceptions.ResourceNotFoundException;
import thb.siprojektanamneseservice.model.MedicationInTake;
import thb.siprojektanamneseservice.model.Person;
import thb.siprojektanamneseservice.repository.MedicationInTakeRepository;
import thb.siprojektanamneseservice.transfert.MedicationInTakeTO;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("MedicationInTakeService")
@Transactional(rollbackOn = Exception.class)
public class MedicationInTakeService {

    private final MedicationInTakeRepository repository;
    private final PersonService personService;

    @Autowired
    public MedicationInTakeService(MedicationInTakeRepository repository, PersonService personService){
        this.repository = repository;
        this.personService = personService;
    }

    public List<MedicationInTake> listAll() {
        return  repository.findAll();
    }

    public List<MedicationInTake> listAllByPersonId(UUID personId) {
        return  repository.findAllByPersonId(personId);
    }

    public MedicationInTake getOne(UUID medicationInTakeId) throws ResourceNotFoundException {
        Optional<MedicationInTake> medicationInTakeOP = repository.findById(medicationInTakeId);
        if (!medicationInTakeOP.isPresent()) {
            throw new ResourceNotFoundException(
                    String.format("The medicationInTake with the id %s does not exist", medicationInTakeId.toString())
            );
        }
        return medicationInTakeOP.get();
    }

    public void delete(UUID medicationInTakeId) {
        getOne(medicationInTakeId);
        repository.deleteById(medicationInTakeId);
    }

    /**
     * @param newMedicationInTakeTO to be created
     * @return The new created medicationInTake
     */
    public MedicationInTake create(MedicationInTakeTO newMedicationInTakeTO) {

        Person personFound = personService.getOne(newMedicationInTakeTO.getPatientId());
        MedicationInTake create = new MedicationInTake();

        create.setId(null);
        create.setStartDate(newMedicationInTakeTO.getStartDate());
        create.setDesignation(newMedicationInTakeTO.getDesignation());
        create.setDosage(newMedicationInTakeTO.getDosage());
        create.setBloodDiluent(newMedicationInTakeTO.isBloodDiluent());
        create.setPerson(personFound);
        return repository.save(create);
    }


    public MedicationInTake update(UUID medicationInTakeId, MedicationInTake update) throws ResourceNotFoundException {
        MedicationInTake medicationInTakeFound = getOne(medicationInTakeId);

        if (!medicationInTakeFound.getId().equals(update.getId())){
            checkForUniqueness(update);
        }
        update.setId(medicationInTakeId);
        return repository.save(update);
    }

    private void checkForUniqueness(MedicationInTake medicationInTake) {
        if (repository.countById(medicationInTake.getId()) > 0){
            throw new ResourceBadRequestException(
                    String.format("A medicationInTake with the id %s already exist", medicationInTake.getId())
            );
        }
    }
}
