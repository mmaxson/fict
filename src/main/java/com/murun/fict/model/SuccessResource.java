package com.murun.fict.model;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;



@JsonIgnoreProperties(ignoreUnknown = true)
public class SuccessResource {

    private String code;
    private String message;
    private final LocalDateTime dateTime = LocalDateTime.now();


    public SuccessResource(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
