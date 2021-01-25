package thb.siprojektanamneseservice.service.authencation;

@SuppressWarnings("serial")
public class AuthenticationSessionRepositoryException extends Exception {

    public AuthenticationSessionRepositoryException(String message) {
        super(message);
    }

    public AuthenticationSessionRepositoryException(String message,
            Throwable cause) {
        super(message, cause);
    }
}
