package com.horecarobot.backend.Exceptions;

public class ValueNotUniqueException extends Exception {
    public ValueNotUniqueException(String errorMessage) {
        super(errorMessage);
    }
}
