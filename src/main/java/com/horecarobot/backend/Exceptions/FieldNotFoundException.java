package com.horecarobot.backend.Exceptions;

public class FieldNotFoundException extends Exception {
    public FieldNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
