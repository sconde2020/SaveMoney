package com.save.money.config;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ErrorMessage {
    private HttpStatus httpStatus;
    private LocalDateTime timestamp;
    private String message;
    private String details;

    public ErrorMessage(HttpStatus httpStatus, String message, String details) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }
}
