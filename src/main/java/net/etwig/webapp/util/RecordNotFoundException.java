package net.etwig.webapp.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Record Not Found")
public class RecordNotFoundException extends RuntimeException{

    public RecordNotFoundException(String msg){
        super(msg);
    }
}
