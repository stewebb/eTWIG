package net.etwig.webapp.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception indicating a portfolio mismatch.
 */

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Portfolio Mismatch")
public class PortfolioMismatchException extends RuntimeException {

    /**
     * Constructs a new PortfolioMismatchException with the specified event organizer portfolio.
     *
     * @param eventOrganizerPortfolio the portfolio that caused the mismatch
     */

    public PortfolioMismatchException(String eventOrganizerPortfolio) {
        super("Your portfolio is not " + eventOrganizerPortfolio);
    }
}