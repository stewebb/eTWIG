package net.etwig.webapp.util;

import java.io.Serial;

public class InvalidDateFormatException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

	public InvalidDateFormatException(String message) {
        super(message);
    }
}
