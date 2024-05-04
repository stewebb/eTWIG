package net.etwig.webapp.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Portfolio Mismatch")
public class PortfolioMismatchException extends RuntimeException {
    public PortfolioMismatchException(String eventOrganizerPortfolio) {
        super("Your portfolio is not " + eventOrganizerPortfolio);
    }
}
