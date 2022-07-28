package com.gowita.exception;

import lombok.Getter;

@Getter
public class CustomNotFoundException extends RuntimeException {
    private final String message;

    public CustomNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
