package com.horecarobot.backend.Exceptions;

public class UnAuthorizedException extends Exception {
    public UnAuthorizedException(String errorMessage) {
        super(errorMessage);
    }
}
