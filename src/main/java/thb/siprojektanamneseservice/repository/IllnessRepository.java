package thb.siprojektanamneseservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import thb.siprojektanamneseservice.model.Illness;

import java.util.List;
import java.util.UUID;

public interface IllnessRepository
        extends JpaRepository<Illness, UUID>, JpaSpecificationExecutor<Illness> {

    int countById(UUID preExistingIllnessId);
    Illness findByName(String name);
}
