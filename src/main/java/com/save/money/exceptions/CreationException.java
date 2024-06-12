package com.save.money.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class CreationException extends Exception {
    public CreationException(String message) {
        super(message);
    }
}
