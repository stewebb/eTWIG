package net.etwig.webapp.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import lombok.AllArgsConstructor;
import lombok.Data;

@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Return an HTTP 400 error if the input date is not well-formed.
	 * @param e
	 * @return
	 */

    // TODO REMOVE ME AND USE ANNOTATIONS ON EXCEPTIONS ONLY.
    /*
    @ExceptionHandler(InvalidDateFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleInvalidDateFormat(InvalidDateFormatException e) {
    	ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

     */
    
    @Data
    @AllArgsConstructor
    public class ErrorResponse {
        private String exception;
    }

}