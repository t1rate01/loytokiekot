package com.example.backend.security.dto;

public class LoginResponseDto {
    private String token;
    private int keepDiscsFor;
    private boolean sharePhonenumber;
    private boolean canPostDiscs;
    private String username;
    private String email;
    private String phonenumber;
    private String region;
    private String city;
    private String description;
    
    
    public LoginResponseDto() {
    }


    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description= description;
    }

    public int getKeepDiscsFor() {
        return this.keepDiscsFor;
    }

    public void setKeepDiscsFor(int keepDiscsFor) {
        this.keepDiscsFor = keepDiscsFor;
    }

    public boolean isSharePhonenumber() {
        return this.sharePhonenumber;
    }

    public boolean getSharePhonenumber() {
        return this.sharePhonenumber;
    }

    public void setSharePhonenumber(boolean sharePhonenumber) {
        this.sharePhonenumber = sharePhonenumber;
    }

    public boolean isCanPostDiscs() {
        return this.canPostDiscs;
    }

    public boolean getCanPostDiscs() {
        return this.canPostDiscs;
    }

    public void setCanPostDiscs(boolean canPostDiscs) {
        this.canPostDiscs = canPostDiscs;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
