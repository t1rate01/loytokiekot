package com.example.backend.security.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UpdateDto {

    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @Email(message = "Email must be valid")
    private String email;

    @Size(min =7, max = 20, message = "Invalid phonenumber")
    private String phonenumber;

    @Size(max = 2000, message = "Description must be less than 2000 characters")
    private String description;


    private String region;

    private String city;

    private Boolean sharePhonenumber;


    public UpdateDto() {
    }

    public UpdateDto(String username, String password, String email, String phonenumber , String region, String city, String description, Boolean sharePhonenumber) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phonenumber = phonenumber;
        this.region = region;
        this.city = city;
        this.description = description;
        this.sharePhonenumber = sharePhonenumber;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region= region;
    }

    public Boolean getSharePhonenumber() {
        return this.sharePhonenumber;
    }
    
    public void setSharePhonenumber(Boolean sharePhonenumber) {
        this.sharePhonenumber = sharePhonenumber;
    }


    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city= city;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return this.phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public UpdateDto username(String username) {
        setUsername(username);
        return this;
    }

    public UpdateDto password(String password) {
        setPassword(password);
        return this;
    }

    public UpdateDto email(String email) {
        setEmail(email);
        return this;
    }

    public UpdateDto phonenumber(String phonenumber) {
        setPhonenumber(phonenumber);
        return this;
    }
 
}
