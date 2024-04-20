package net.etwig.webapp.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid action")
public class InvalidActionException extends RuntimeException {
    public InvalidActionException(String action) {
        super("Action " + action.toUpperCase() + " is invalid.");
    }
}
