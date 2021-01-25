package thb.siprojektanamneseservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thb.siprojektanamneseservice.exceptions.ResourceBadRequestException;
import thb.siprojektanamneseservice.exceptions.ResourceNotFoundException;
import thb.siprojektanamneseservice.model.Address;
import thb.siprojektanamneseservice.repository.AddressRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("AddressService")
@Transactional(rollbackOn = Exception.class)
public class AddressService {

    private final AddressRepository repository;

    @Autowired
    public AddressService(AddressRepository repository){
        this.repository = repository;
    }

    public List<Address> listAll() {
        return  repository.findAll();
    }

    public Address getOne(UUID addressId) throws ResourceNotFoundException {
        Optional<Address> addressOP = repository.findById(addressId);
        if (!addressOP.isPresent()) {
            throw new ResourceNotFoundException(
                    String.format("The address with the id %s does not exist", addressId.toString())
            );
        }
        return addressOP.get();
    }

    public void delete(UUID addressId) {
        getOne(addressId);
        repository.deleteById(addressId);
    }

    /**
     * @param newAddress to be created
     * @return The new created address
     */
    public Address create(Address newAddress) {
        checkForUniqueness(newAddress);
        newAddress.setId(null);
        return repository.save(newAddress);
    }

    public Address update(UUID addressId, Address update) throws ResourceNotFoundException {
        Address addressFound = getOne(addressId);

        if (!addressFound.getId().equals(update.getId())){
            checkForUniqueness(update);
        }
        update.setId(addressId);
        return repository.save(update);
    }

    private void checkForUniqueness(Address address) {
        if (repository.countById(address.getId()) > 0){
            throw new ResourceBadRequestException(
                    String.format("A address with the id %s already exist", address.getId())
            );
        }
    }
}
