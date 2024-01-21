package net.grinecraft.etwig.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import net.grinecraft.etwig.util.exception.InvalidDateFormatException;

@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Return an HTTP 400 error if the input date is not well-formed.
	 * @param e
	 * @return
	 */
	
    @ExceptionHandler(InvalidDateFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleInvalidDateFormat(InvalidDateFormatException e) {
    	ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    @Data
    @AllArgsConstructor
    public class ErrorResponse {
        private String exception;
    }

}