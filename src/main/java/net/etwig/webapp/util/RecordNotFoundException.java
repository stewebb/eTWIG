package net.etwig.webapp.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class for handling scenarios where a record is not found.
 * <p>
 * This exception is thrown when a requested record cannot be located,
 * triggering a HTTP 404 (Not Found) status in the response.
 * The reason for the HTTP status is directly specified as "Record Not Found".
 * </p>
 *
 * @ResponseStatus Specifies that this exception class will cause a response
 * with HTTP status 404 (Not Found) and a reason phrase "Record Not Found".
 * This is particularly useful in REST APIs where the client needs clear feedback
 * that the requested resource is unavailable.
 *
 * @param msg the detail message. The detail message is saved for
 * later retrieval by the {@link Throwable#getMessage()} method.
 */

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Record Not Found")
public class RecordNotFoundException extends RuntimeException {

    /**
     * Constructs a new record not found exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a call to
     * {@link Throwable#initCause(java.lang.Throwable)}.
     *
     * @param msg the detail message. The detail message is saved for
     * later retrieval by the {@link Throwable#getMessage()} method.
     */

    public RecordNotFoundException(String msg) {
        super(msg);
    }
}
