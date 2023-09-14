package com.example.backend.discs.dto;

import java.util.List;


public class DiscDto {   // DTO For the post process, frontend sends a disc + list of keywords, which are stored in different tables
    private String discname;
    private List<String> keywords;
    private String region;
    private String city;


    public DiscDto() {
    }

    public DiscDto(String discname,List<String> keywords) {
        this.discname = discname;
        this.keywords = keywords;
    }

    public List<String> getKeywords() {
        return this.keywords;
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

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public DiscDto keywords(List<String> keywords) {
        setKeywords(keywords);
        return this;
    }

    public String getDiscname() {
        return this.discname;
    }

    public void setDiscname(String discname) {
        this.discname = discname;
    }

    public DiscDto discname(String discname) {
        setDiscname(discname);
        return this;
    }
    
}
