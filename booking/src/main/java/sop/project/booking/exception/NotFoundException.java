package sop.project.booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class NotFoundException extends RuntimeException {
    public NotFoundException(String msg){
        super(msg);
    }

    public NotFoundException(String msg, Throwable e) {
        super(msg, e);
    }
}