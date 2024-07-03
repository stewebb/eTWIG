package net.etwig.webapp.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class to signify a portfolio mismatch error.
 * This exception is thrown when there is an attempt to access or modify resources within a portfolio
 * that does not match the user's authorized portfolio, indicating a forbidden operation.
 * This class extends {@link RuntimeException}, thus, it is unchecked.
 * <p>
 * This exception triggers a response with HTTP status code 403 (Forbidden) to indicate
 * that the server understood the request but refuses to authorize it.
 *
 * @ResponseStatus Marks the exception class with the HTTP status code and reason that should be returned.
 *                 The {@code HttpStatus.FORBIDDEN} is used here to denote that the access is denied.
 */

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Portfolio Mismatch")
public class PortfolioMismatchException extends RuntimeException {

    /**
     * Constructs a new PortfolioMismatchException with a detailed message about the portfolio causing the mismatch.
     * The message constructed provides clarity on what specific portfolio was expected versus what was provided.
     *
     * @param eventOrganizerPortfolio The specific portfolio that was expected, triggering this exception when not met.
     */

    public PortfolioMismatchException(String eventOrganizerPortfolio) {
        super("Your portfolio is not " + eventOrganizerPortfolio);
    }
}