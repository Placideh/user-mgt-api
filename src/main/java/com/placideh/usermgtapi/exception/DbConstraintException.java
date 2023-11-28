package com.placideh.usermgtapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class DbConstraintException  extends  RuntimeException {
    public DbConstraintException(String message) {
        super(message);
    }

}
