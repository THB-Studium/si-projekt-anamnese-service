package thb.siprojektanamneseservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import thb.siprojektanamneseservice.model.FamilyAnamnesis;

import java.util.UUID;

public interface FamilyAnamnesisRepository
        extends JpaRepository<FamilyAnamnesis, UUID>, JpaSpecificationExecutor<FamilyAnamnesis> {

    int countById(UUID familyAnamnesisId);
}
