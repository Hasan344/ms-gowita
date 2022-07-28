package com.gowita.exception.handler;

import com.gowita.dto.response.ErrorResponse;
import com.gowita.exception.AlreadyExistException;
import com.gowita.exception.AuthenticationException;
import com.gowita.exception.CustomBadRequestException;
import com.gowita.exception.CustomNotFoundException;
import com.gowita.exception.FileException;
import com.gowita.exception.ForbiddenException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandling extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        log.error("Exception: {}", ex.getMessage());
        Map<String, String> checks = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            checks.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        ErrorResponse errorResponse = new ErrorResponse("Bad request", checks);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AlreadyExistException.class)
    protected ErrorResponse handleAlreadyExistException(AlreadyExistException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage());
        log.info("{}", response);
        return response;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CustomNotFoundException.class)
    protected ErrorResponse handleCustomNotFoundException(
            CustomNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage());
        log.info("{}", response);
        return response;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    protected ErrorResponse authException(AuthenticationException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage());
        log.info("{}", response);
        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomBadRequestException.class)
    protected ErrorResponse handleCustomBadRequestException(
            CustomBadRequestException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage());
        log.info("{}", response);
        return response;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(FileException.class)
    protected ErrorResponse handleCustomBadRequestException(
            FileException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage());
        log.info("{}", response);
        return response;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    protected ErrorResponse handleForbiddenException(
            ForbiddenException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage());
        log.info("{}", response);
        return response;
    }
}
