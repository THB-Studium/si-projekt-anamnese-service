package thb.siprojektanamneseservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class ResourceUnauthorizedException extends RuntimeException {

    public ResourceUnauthorizedException() {
        super();
    }

    public ResourceUnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceUnauthorizedException(String message) {
        super(message);
    }

    public ResourceUnauthorizedException(Throwable cause) {
        super(cause);
    }
}