package com.horecarobot.backend.Exceptions;

import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

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
    private ResponseEntity<String> error(HttpStatus status, Exception e) {
        return ResponseEntity.status(status).body(e.getMessage());
    }
}
