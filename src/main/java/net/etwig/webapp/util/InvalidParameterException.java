package net.etwig.webapp.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class for handling invalid parameter scenarios.
 * <p>
 * This exception is thrown when a method parameter is not valid,
 * triggering a HTTP 400 (Bad Request) status in the response.
 * The reason for the HTTP status is directly specified as "Invalid Parameter".
 * </p>
 *
 * @ResponseStatus Specifies that this exception class will cause a response
 * with HTTP status 400 (Bad Request) and a reason phrase "Invalid Parameter".
 * This is helpful in REST APIs where the client needs to understand
 * that the error was due to an invalid parameter sent in the request.
 *
 * @param msg the detail message. The detail message is saved for
 * later retrieval by the {@link Throwable#getMessage()} method.
 */

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Parameter")
public class InvalidParameterException extends IllegalArgumentException {

    /**
     * Constructs a new invalid parameter exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a call to
     * {@link Throwable#initCause(java.lang.Throwable)}.
     *
     * @param msg the detail message. The detail message is saved for
     * later retrieval by the {@link Throwable#getMessage()} method.
     */

    public InvalidParameterException(String msg) {
        super(msg);
    }
}