package thb.siprojektanamneseservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import thb.siprojektanamneseservice.model.Diagnosis;

import java.util.List;
import java.util.UUID;

public interface DiagnosisRepository
        extends JpaRepository<Diagnosis, UUID>, JpaSpecificationExecutor<Diagnosis> {

    int countById(UUID diagnosisId);
    List<Diagnosis> findAllByPersonId(UUID personId);
}
