package com.iresh.model;

/**
 * Created by iresh on 11/19/2016.
 */
public class ErrorObjects {
    private String errorField;
    private String errorMessage;

    public ErrorObjects(){}

    public ErrorObjects(String errorField, String errorMessage) {
        this.errorField = errorField;
        this.errorMessage = errorMessage;
    }

    public String getErrorField() {
        return errorField;
    }

    public void setErrorField(String errorField) {
        this.errorField = errorField;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
