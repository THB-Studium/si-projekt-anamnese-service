package thb.siprojektanamneseservice.rest.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.*;
import thb.siprojektanamneseservice.exceptions.ResourceUnauthorizedException;
import thb.siprojektanamneseservice.rest.ApiConstants;
import thb.siprojektanamneseservice.service.authencation.AuthenticationService;
import thb.siprojektanamneseservice.service.authencation.Credentials;
import thb.siprojektanamneseservice.utils.UrlUtils;

import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin(origins = ApiConstants.CROSS_ORIGIN_PATH)
public class SessionsRootController {
    private static final Logger log = LoggerFactory.getLogger(SessionsRootController.class);
    private final AuthenticationService authenticationService;

    @Autowired
    public SessionsRootController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @RequestMapping(path=ApiConstants.SESSIONS_COLLECTION, method = RequestMethod.POST)
    public OAuth2AccessToken login(@RequestHeader HttpHeaders headers) {

        @SuppressWarnings("static-access")
        List<String> authorizations = headers.get(headers.AUTHORIZATION);

        if (authorizations != null && authorizations.isEmpty()) {
            throw new ResourceUnauthorizedException("an authorization credential is required");
        }

        String authorization = Objects.requireNonNull(authorizations).get(0);

        Credentials credentials = UrlUtils.extractCredentialsFromBasicAuthorization(authorization);
        if (credentials == null) {
            throw new ResourceUnauthorizedException(
                    String.format("failed to extract credentials from auth header '%s'", authorization));
        }

        // get username
        String username = credentials.getUsername();
        if (username == null || username.isEmpty()) {
            throw new RuntimeException("the username must not be null and not be empty");
        }
        log.debug("attempt to login as '{}'", username);

        // create session
        return authenticationService.createAuthenticationSession(credentials);
    }

    @RequestMapping(path = ApiConstants.SESSIONS_ITEM, method = RequestMethod.DELETE)
    public void logout(@PathVariable("sessionId") String sessionId) {
        authenticationService.deleteAuthenticationSession(sessionId);
        log.debug("session '{}' deleted", sessionId);
    }

    @RequestMapping(path = ApiConstants.SESSIONS_ITEM_DETAILS, method = RequestMethod.GET)
    public OAuth2AccessToken getUserDetails(@PathVariable("sessionId") String sessionId) {
        return authenticationService.getAuthenticationSession(sessionId);
    }

}
