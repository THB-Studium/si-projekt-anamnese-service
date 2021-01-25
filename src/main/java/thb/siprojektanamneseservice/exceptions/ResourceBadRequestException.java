package thb.siprojektanamneseservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ResourceBadRequestException extends RuntimeException {

    public ResourceBadRequestException() {
        super();
    }

    public ResourceBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceBadRequestException(String message) {
        super(message);
    }

    public ResourceBadRequestException(Throwable cause) {
        super(cause);
    }
}
