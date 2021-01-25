package thb.siprojektanamneseservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import thb.siprojektanamneseservice.model.Security;

import java.util.UUID;

public interface SecurityRepository
        extends JpaRepository<Security, UUID>, JpaSpecificationExecutor<Security> {

    int countById(UUID securityId);
    Security findBySecretQuestionAndAndAnswer(String secretQuestion, String answer);
}