package thb.siprojektanamneseservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import thb.siprojektanamneseservice.model.Address;

import java.util.UUID;

public interface AddressRepository
        extends JpaRepository<Address, UUID>, JpaSpecificationExecutor<Address> {

    int countById(UUID addressId);
}