package thb.siprojektanamneseservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import thb.siprojektanamneseservice.model.VegetativeAnamnesis;

import java.util.UUID;

public interface VegetativeAnamnesisRepository
        extends JpaRepository<VegetativeAnamnesis, UUID>, JpaSpecificationExecutor<VegetativeAnamnesis> {

    int countById(UUID vegetativeAnamnesisId);

}
