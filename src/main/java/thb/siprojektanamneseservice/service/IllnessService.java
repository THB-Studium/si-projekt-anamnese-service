package thb.siprojektanamneseservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thb.siprojektanamneseservice.exceptions.ResourceBadRequestException;
import thb.siprojektanamneseservice.exceptions.ResourceNotFoundException;
import thb.siprojektanamneseservice.model.Illness;
import thb.siprojektanamneseservice.repository.IllnessRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("PreExistingIllnessService")
@Transactional(rollbackOn = Exception.class)
public class IllnessService {

    private final IllnessRepository repository;

    @Autowired
    public IllnessService(IllnessRepository repository){
        this.repository = repository;
    }

    public List<Illness> listAll() {
        return  repository.findAll();
    }

    public Illness getOne(UUID preExistingIllnessId) throws ResourceNotFoundException {
        Optional<Illness> preExistingIllnessOP = repository.findById(preExistingIllnessId);
        if (!preExistingIllnessOP.isPresent()) {
            throw new ResourceNotFoundException(
                    String.format("The preExistingIllness with the id %s does not exist", preExistingIllnessId.toString())
            );
        }
        return preExistingIllnessOP.get();
    }

    public void delete(UUID preExistingIllnessId) {
        getOne(preExistingIllnessId);
        repository.deleteById(preExistingIllnessId);
    }

    /**
     * @param newIllness to be created
     * @return The new created preExistingIllness
     */
    public Illness create(Illness newIllness) {
        checkForUniqueness(newIllness);
        newIllness.setId(null);
        return repository.save(newIllness);
    }


    public Illness update(UUID preExistingIllnessId, Illness update) throws ResourceNotFoundException {
        Illness illnessFound = getOne(preExistingIllnessId);

        if (!illnessFound.getId().equals(update.getId())){
            checkForUniqueness(update);
        }
        update.setId(preExistingIllnessId);
        return repository.save(update);
    }

    private void checkForUniqueness(Illness illness) {
        if (repository.countById(illness.getId()) > 0){
            throw new ResourceBadRequestException(
                    String.format("A preExistingIllness with the id %s already exist", illness.getId())
            );
        }
    }
}
