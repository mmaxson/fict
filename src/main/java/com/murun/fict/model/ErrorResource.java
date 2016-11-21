package com.murun.fict.model;


public class ErrorResource {

    private String errorCode;
    private String errorMessage;


    public ErrorResource(String code, String message) {
        this.errorCode = code;
        this.errorMessage = message;
    }

    public String getErrorCode() { return errorCode; }

    public void setErrorCode(String errorCode) { this.errorCode = errorCode; }

    public String getErrorMessage() { return errorMessage; }

    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

}
