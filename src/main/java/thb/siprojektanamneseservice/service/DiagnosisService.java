package thb.siprojektanamneseservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thb.siprojektanamneseservice.exceptions.ResourceBadRequestException;
import thb.siprojektanamneseservice.exceptions.ResourceNotFoundException;
import thb.siprojektanamneseservice.model.Diagnosis;
import thb.siprojektanamneseservice.model.Person;
import thb.siprojektanamneseservice.repository.DiagnosisRepository;
import thb.siprojektanamneseservice.transfert.DiagnosisTO;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("DiagnosisService")
@Transactional(rollbackOn = Exception.class)
public class DiagnosisService {

    private final DiagnosisRepository repository;
    private final PersonService personService;

    @Autowired
    public DiagnosisService(DiagnosisRepository repository, PersonService personService){
        this.repository = repository;
        this.personService = personService;
    }

    public List<Diagnosis> listAll() {
        return  repository.findAll();
    }

    public List<Diagnosis> listAllByPersonId(UUID personId) { return  repository.findAllByPersonId(personId); }

    public Diagnosis getOne(UUID diagnosisId) throws ResourceNotFoundException {
        Optional<Diagnosis> diagnosisOP = repository.findById(diagnosisId);
        if (!diagnosisOP.isPresent()) {
            throw new ResourceNotFoundException(
                    String.format("The diagnosis with the id %s does not exist", diagnosisId.toString())
            );
        }
        return diagnosisOP.get();
    }

    public void delete(UUID diagnosisId) {
        getOne(diagnosisId);
        repository.deleteById(diagnosisId);
    }

    /**
     * @param diagnosisTO to be created
     * @return The new created diagnosis
     */
    public Diagnosis create(DiagnosisTO diagnosisTO) {
        Person personFound = personService.getOne(diagnosisTO.getPersonId());
        Diagnosis newDiagnosis = new Diagnosis();

        newDiagnosis.setId(null);
        newDiagnosis.setBodyRegion(diagnosisTO.getBodyRegion());
        newDiagnosis.setExaminationDate(diagnosisTO.getExaminationDate());
        newDiagnosis.setExaminationName(diagnosisTO.getExaminationName());
        newDiagnosis.setPerson(personFound);
        return repository.save(newDiagnosis);
    }


    public Diagnosis update(UUID diagnosisId, Diagnosis update) throws ResourceNotFoundException {
        Diagnosis personFound = getOne(diagnosisId);

        if (!personFound.getId().equals(update.getId())){
            checkForUniqueness(update);
        }
        update.setId(diagnosisId);
        return repository.save(update);
    }

    private void checkForUniqueness(Diagnosis diagnosis) {
        if (repository.countById(diagnosis.getId()) > 0){
            throw new ResourceBadRequestException(
                    String.format("A diagnosis with the id %s already exist", diagnosis.getId())
            );
        }
    }
}
