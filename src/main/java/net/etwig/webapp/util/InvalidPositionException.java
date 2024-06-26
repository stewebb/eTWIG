package net.etwig.webapp.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class for handling scenarios where a user does not have the required permissions for a certain position.
 * <p>
 * This exception is thrown when a user attempts to access or assume a position or role for which they are not authorized,
 * triggering a HTTP 403 (Forbidden) status in the response. The reason for the HTTP status is directly specified as "Invalid Position".
 * </p>
 *
 * @ResponseStatus Specifies that this exception class will cause a response
 * with HTTP status 403 (Forbidden) and a reason phrase "Invalid Position".
 * This is helpful in REST APIs where the client needs to understand
 * that the error was due to unauthorized access attempts to a specific position.
 *
 * @param msg the detail message. The detail message is saved for
 * later retrieval by the {@link Throwable#getMessage()} method.
 */

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Invalid Position")
public class InvalidPositionException extends IllegalArgumentException {

    /**
     * Constructs a new invalid position exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a call to
     * {@link Throwable#initCause(Throwable)}.
     *
     * @param msg the detail message. The detail message is saved for
     * later retrieval by the {@link Throwable#getMessage()} method.
     */

    public InvalidPositionException(String msg) {
        super(msg);
    }
}