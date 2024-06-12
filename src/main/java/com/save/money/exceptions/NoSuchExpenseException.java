package com.save.money.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchExpenseException extends Exception {
    public NoSuchExpenseException(String message) {
        super(message);
    }
}
