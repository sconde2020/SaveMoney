package com.save.money.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class UpdateException extends RuntimeException {
    public UpdateException(String message) {
        super(message);
    }
}
