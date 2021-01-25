package thb.siprojektanamneseservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thb.siprojektanamneseservice.exceptions.ResourceBadRequestException;
import thb.siprojektanamneseservice.exceptions.ResourceNotFoundException;
import thb.siprojektanamneseservice.model.Security;
import thb.siprojektanamneseservice.repository.SecurityRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("SecurityService")
@Transactional(rollbackOn = Exception.class)
public class SecurityService {

    private final SecurityRepository repository;

    @Autowired
    public SecurityService(SecurityRepository repository){
        this.repository = repository;
    }

    public List<Security> listAll() {
        return  repository.findAll();
    }

    public Security getOne(UUID securityId) throws ResourceNotFoundException {
        Optional<Security> securityOP = repository.findById(securityId);
        if (!securityOP.isPresent()) {
            throw new ResourceNotFoundException(
                    String.format("The security with the id %s does not exist", securityId.toString())
            );
        }
        return securityOP.get();
    }

    public void delete(UUID securityId) {
        getOne(securityId);
        repository.deleteById(securityId);
    }

    /**
     * @param newSecurity need to remember password
     * @return The new created security
     */
    public Security create(Security newSecurity) {
        checkForUniqueness(newSecurity);
        newSecurity.setId(null);
        return repository.save(newSecurity);
    }


    public Security update(UUID securityId, Security update) throws ResourceNotFoundException {
        Security securityFound = getOne(securityId);

        if (!securityFound.getId().equals(update.getId())){
            checkForUniqueness(update);
        }
        update.setId(securityId);
        return repository.save(update);
    }

    private void checkForUniqueness(Security security) {
        if (repository.countById(security.getId()) > 0){
            throw new ResourceBadRequestException(
                    String.format("A security with the id %s already exist", security.getId())
            );
        }
    }
}
