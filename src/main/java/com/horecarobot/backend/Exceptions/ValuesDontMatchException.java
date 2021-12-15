package com.horecarobot.backend.Exceptions;

public class ValuesDontMatchException extends Exception {
    public ValuesDontMatchException(String errorMessage) {
        super(errorMessage);
    }
}
