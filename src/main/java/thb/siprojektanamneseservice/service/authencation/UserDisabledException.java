package thb.siprojektanamneseservice.service.authencation;

@SuppressWarnings("serial")
public class UserDisabledException extends Exception {

    public UserDisabledException(String username) {
        super(String.format("the user '%s' is disabled", username));
    }
}
