package thb.siprojektanamneseservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thb.siprojektanamneseservice.exceptions.ResourceBadRequestException;
import thb.siprojektanamneseservice.exceptions.ResourceNotFoundException;
import thb.siprojektanamneseservice.model.Disease;
import thb.siprojektanamneseservice.model.Illness;
import thb.siprojektanamneseservice.model.Person;
import thb.siprojektanamneseservice.repository.DiseaseRepository;
import thb.siprojektanamneseservice.repository.IllnessRepository;
import thb.siprojektanamneseservice.transfert.DiseaseTO;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("DiseaseService")
@Transactional(rollbackOn = Exception.class)
public class DiseaseService {

    private final DiseaseRepository repository;
    private final IllnessRepository illnessRepository;
    private final PersonService personService;

    @Autowired
    public DiseaseService(DiseaseRepository repository, IllnessRepository illnessRepository, PersonService personService){ this.repository = repository;
        this.illnessRepository = illnessRepository;
        this.personService = personService;
    }

    public List<Disease> listAll() { return  repository.findAll(); }

    public List<Disease> listAllByPersonId(UUID personId) { return  repository.findAllByPersonId(personId); }

    public Disease getOne(UUID diseaseId) throws ResourceNotFoundException {
        Optional<Disease> diseaseOP = repository.findById(diseaseId);
        if (!diseaseOP.isPresent()) {
            throw new ResourceNotFoundException(
                    String.format("The disease with the id %s does not exist", diseaseId.toString())
            );
        }
        return diseaseOP.get();
    }

    public void delete (UUID diseaseId) {
        getOne(diseaseId);
        repository.deleteById(diseaseId);
    }

    /**
     * @param newDiseaseTO to be created
     * @return The new created disease
     */
    public Disease create(DiseaseTO newDiseaseTO) {

        Person personFound = personService.getOne(newDiseaseTO.getPatientId());
        Disease newDisease  = new Disease();

        if (!newDiseaseTO.getPreExistingIllnesses().isEmpty()){
            newDiseaseTO.getPreExistingIllnesses().forEach(illness -> {
                Illness illnessFound = illnessRepository.findByName(illness.getName());
                if (illnessFound == null){
                    illnessFound = illnessRepository.saveAndFlush(illness);
                }
                newDisease.getPreExistingIllnesses().add(illnessFound);
            });
        }

        newDisease.setId(null);
        newDisease.setPerson(personFound);
        newDisease.setSurgeriesDetails(newDiseaseTO.getSurgeriesDetails());
        newDisease.setUndergoneSurgery(newDiseaseTO.isUndergoneSurgery());

        return repository.save(newDisease);
    }


    public Disease update(UUID diseaseId, Disease update) throws ResourceNotFoundException {
        Disease diseaseFound = getOne(diseaseId);

        if (!diseaseFound.getId().equals(update.getId())){
            checkForUniqueness(update);
        }
        update.setId(diseaseId);
        return repository.save(update);
    }

    private void checkForUniqueness(Disease disease) {
        if (repository.countById(disease.getId()) > 0){
            throw new ResourceBadRequestException(
                    String.format("A disease with the id %s already exist", disease.getId())
            );
        }
    }
}
