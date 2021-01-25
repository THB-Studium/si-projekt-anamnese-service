package thb.siprojektanamneseservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thb.siprojektanamneseservice.exceptions.ResourceUnauthorizedException;
import thb.siprojektanamneseservice.model.Person;
import thb.siprojektanamneseservice.repository.PersonRepository;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Service
@Transactional(rollbackFor = Exception.class)
public class TokenService {

    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private PersonRepository personRepository;

    public void revoke(String authorization) {

        if (authorization != null) {
            String token = authorization.trim().replace("Bearer ", "");
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
            tokenStore.removeAccessToken(accessToken);
        } else {
            throw new ResourceUnauthorizedException("The bearer token must not be null");
        }

    }

    public Person getConnectedUser(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        if (principal == null) {
            return null;
        } else {
            String username = principal.getName();
            Person person = personRepository.findByUserName(username);
            return person;
        }
    }

}