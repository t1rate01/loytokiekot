package com.example.backend.discs;

import java.util.List;


public class DiscDto {   // DTO For the post process, frontend sends a disc + list of keywords, which are stored in different tables
    private String discname;
    private List<String> keywords;


    public DiscDto() {
    }

    public DiscDto(String discname,List<String> keywords) {
        this.discname = discname;
        this.keywords = keywords;
    }

    public List<String> getKeywords() {
        return this.keywords;
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
