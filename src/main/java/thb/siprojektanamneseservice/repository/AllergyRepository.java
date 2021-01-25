package thb.siprojektanamneseservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import thb.siprojektanamneseservice.model.Allergy;

import java.util.UUID;

public interface AllergyRepository
        extends JpaRepository<Allergy, UUID>, JpaSpecificationExecutor<Allergy> {

    int countById(UUID allergyTypeId);
    Allergy findByName(String name);
}
