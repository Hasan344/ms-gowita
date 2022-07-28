package com.gowita.exception;

import lombok.Getter;

@Getter
public class CustomBadRequestException extends RuntimeException {
    private final String message;

    public CustomBadRequestException(String message) {
        super(message);
        this.message = message;
    }
}
