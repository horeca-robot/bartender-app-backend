package com.horecarobot.backend.Views;

public class BasicView {
    private String message = "";

    public BasicView() {
        // Default constructor...
    }

    public BasicView(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "{" +
            " message='" + getMessage() + "'" +
            "}";
    }    
}
