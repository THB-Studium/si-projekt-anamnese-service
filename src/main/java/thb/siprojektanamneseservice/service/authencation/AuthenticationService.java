package thb.siprojektanamneseservice.service.authencation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import thb.siprojektanamneseservice.config.AppSettings;
import thb.siprojektanamneseservice.exceptions.ResourceNotFoundException;
import thb.siprojektanamneseservice.exceptions.ResourceUnauthorizedException;
import thb.siprojektanamneseservice.model.Person;
import thb.siprojektanamneseservice.repository.PersonRepository;
import thb.siprojektanamneseservice.transfert.user.User2UserReadTO;

import javax.transaction.Transactional;
import java.util.*;


@Service
@Transactional(rollbackOn = Exception.class)
public class AuthenticationService {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AppSettings settings;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * create an authentication session in the session repository with full
     * resolution of reachable roles and groups
     *
     * @return the id of the session
     * @throws AuthenticationSessionRepositoryException
     * @throws UserDisabledException
     */
    public OAuth2AccessToken createAuthenticationSession(Credentials credentials) {
        // get person information
        Person person = personRepository.findByUserName(credentials.getUsername());
        if (person == null) {
            throw new ResourceNotFoundException(
                    String.format("the username %s does not exist", credentials.getUsername()));
        }

        if (!passwordEncoder.matches(credentials.getPassword(), person.getPassword())) {
            throw new ResourceUnauthorizedException(
                    String.format("The password and the username '%s' do not match", credentials.getUsername()));
        }

        TypeReference<Map<String, Object>> mapType = new TypeReference<Map<String, Object>>() {
        };

        // store the session
        DefaultOAuth2AccessToken accessToken = new DefaultOAuth2AccessToken(person.getUserName());
        accessToken.setTokenType("bearer");
        accessToken.setValue(UUID.randomUUID().toString());
        accessToken.setExpiration(DateUtils.addSeconds(new Date(), settings.getAccessTokenValidity()));
        accessToken.setAdditionalInformation(objectMapper.convertValue(User2UserReadTO.apply(person), mapType));

        // define person roles
        List<GrantedAuthority> authorities = new ArrayList<>();
        person.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

        // create authentication
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                person.getUserName(), person.getPassword(), authorities);

        String clientId = UUID.randomUUID().toString();

        OAuth2Request request = new OAuth2Request(
                null, clientId, authorities, true, null, null, null, null, null);

        OAuth2Authentication oauth2Authentication = new OAuth2Authentication(request, authentication);

        tokenStore.storeAccessToken(accessToken, oauth2Authentication);

        return tokenStore.readAccessToken(accessToken.getValue());
    }

    /**
     * delete an authentication session from the session repository
     *
     * @param sessionId
     * @throws AuthenticationSessionRepositoryException
     */
    public void deleteAuthenticationSession(String sessionId) {
        // delete the session id from the session store
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(sessionId);

        try {
            tokenStore.removeAccessToken(accessToken);
        } catch (Exception e) {
            log.warn("could not remove access token for session id {}. Reason: ", e.getMessage());
        }
    }

    /**
     * get an authentication session from the session repository
     *
     * @param sessionId
     * @return
     * @throws AuthenticationSessionRepositoryException
     */
    public OAuth2AccessToken getAuthenticationSession(String sessionId) {
        // get the authentication session
        return tokenStore.readAccessToken(sessionId);
    }

}
