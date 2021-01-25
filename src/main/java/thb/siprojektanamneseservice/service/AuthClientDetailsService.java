package thb.siprojektanamneseservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;
import thb.siprojektanamneseservice.model.AuthClientDetails;
import thb.siprojektanamneseservice.repository.AuthClientRepository;

import java.util.List;
import java.util.Set;

@Service
public class AuthClientDetailsService implements ClientDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(AuthClientDetailsService.class);

    @Autowired
    private AuthClientRepository authClientRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public ClientDetails loadClientByClientId(String clientId) {
        return authClientRepository.findByClientId(clientId).orElseThrow(IllegalArgumentException::new);
    }

    public AuthClientDetails createClient(String clientId, String clientPassword, Set<String> clientScopes,
                                          Set<String> clientGrantTypes) {
        List<AuthClientDetails> existingClients = authClientRepository.findAllByClientId(clientId);

        if (!existingClients.isEmpty()) {
            logger.warn(String.format("Could not create the client '%s'. This client exists already.", clientId));
            return null;
        } else {
            AuthClientDetails client = new AuthClientDetails();
            client.setClientId(clientId);
            client.setClientSecret(encoder.encode(clientPassword));
            client.setScopes(String.join(",", clientScopes));
            client.setGrantTypes(String.join(",", clientGrantTypes));

            return authClientRepository.save(client);
        }
    }
}
