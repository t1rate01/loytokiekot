package com.example.backend.security.dto;

public class LoginErrorDto  {

    private String errorMessage;

    public LoginErrorDto() {
    }

    public LoginErrorDto(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
}
