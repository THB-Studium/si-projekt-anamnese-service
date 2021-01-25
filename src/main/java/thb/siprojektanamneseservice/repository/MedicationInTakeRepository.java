package thb.siprojektanamneseservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import thb.siprojektanamneseservice.model.MedicationInTake;

import java.util.List;
import java.util.UUID;

public interface MedicationInTakeRepository
        extends JpaRepository<MedicationInTake, UUID>, JpaSpecificationExecutor<MedicationInTake> {

    int countById(UUID medicationInTakeId);
    List<MedicationInTake> findAllByPersonId(UUID personId);
}


