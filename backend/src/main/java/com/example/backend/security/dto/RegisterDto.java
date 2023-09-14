package com.example.backend.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Objects;

public class RegisterDto {

    @NotBlank(message = "Username cannot be empty")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Phonenumber cannot be empty")
    @Size(min =7, max = 20, message = "Invalid phonenumber")
    private String phonenumber;

    @Size(max = 2000, message = "Description must be less than 2000 characters")
    private String description;

    private String region;
    private String city;

    public RegisterDto() {
    }

    public RegisterDto(String username, String password, String email, String phonenumber , String region, String city, String description) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phonenumber = phonenumber;
        this.region = region;
        this.city = city;
        this.description = description;
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

    public RegisterDto username(String username) {
        setUsername(username);
        return this;
    }

    public RegisterDto password(String password) {
        setPassword(password);
        return this;
    }

    public RegisterDto email(String email) {
        setEmail(email);
        return this;
    }

    public RegisterDto phonenumber(String phonenumber) {
        setPhonenumber(phonenumber);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof RegisterDto)) {
            return false;
        }
        RegisterDto registerDto = (RegisterDto) o;
        return Objects.equals(username, registerDto.username) && Objects.equals(password, registerDto.password) && Objects.equals(email, registerDto.email) && Objects.equals(phonenumber, registerDto.phonenumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, email, phonenumber);
    }

    @Override
    public String toString() {
        return "{" +
            " username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", email='" + getEmail() + "'" +
            ", phonenumber='" + getPhonenumber() + "'" +
            "}";
    }

    
}
