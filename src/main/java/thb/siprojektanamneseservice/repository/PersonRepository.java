package thb.siprojektanamneseservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import thb.siprojektanamneseservice.model.Address;
import thb.siprojektanamneseservice.model.Person;

import java.util.UUID;

public interface PersonRepository
        extends JpaRepository<Person, UUID>, JpaSpecificationExecutor<Person> {

    int countById(UUID personId);
    Person findByUserName(String userName);
    Person findByIdAndAllergiesContaining(UUID personId, String allergyName);

}
