package com.gowita.exception;

import lombok.Getter;

@Getter
public class FileException extends RuntimeException {
    private final String message;

    public FileException(String message) {
        super(message);
        this.message = message;
    }
}
