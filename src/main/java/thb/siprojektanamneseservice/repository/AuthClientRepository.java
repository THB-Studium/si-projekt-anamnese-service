package thb.siprojektanamneseservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thb.siprojektanamneseservice.model.AuthClientDetails;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthClientRepository extends JpaRepository<AuthClientDetails, String> {

    Optional<AuthClientDetails> findByClientId(String clientId);
    List<AuthClientDetails> findAllByClientId(String clientId);

}
