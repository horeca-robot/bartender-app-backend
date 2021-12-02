package com.horecarobot.backend.Exceptions;

import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ServiceErrorAdvice {

    @ExceptionHandler({ NotFoundException.class })
    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        return error(NOT_FOUND, e);
    }

    @ExceptionHandler({ IllegalStateException.class })
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException e) {
        return error(INTERNAL_SERVER_ERROR, e);
    }

    @ExceptionHandler({ ValueNotUniqueException.class })
    public ResponseEntity<String> handleValueNotUniqueException(ValueNotUniqueException e) {
        return error(BAD_REQUEST, e);
    }

    private ResponseEntity<String> error(HttpStatus status, Exception e) {
        return ResponseEntity.status(status).body(e.getMessage());
    }
}
