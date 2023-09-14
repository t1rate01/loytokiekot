package com.example.backend.discs.dto;

import java.util.stream.Collectors;

import com.example.backend.discs.Disc;
import com.example.backend.keywords.DiscKeyword;

import java.util.List;

public class GetDiscDto { // DTO to avoid lazy loading errors
    
    private Long id;
    private String discname;
    private String postedBy;
    private List<String> discKeywords;
    private boolean notified;
    private String region;
    private String city;

    public GetDiscDto(Disc disc) {
        this.id = disc.getId();
        this.discname = disc.getDiscname();
        this.postedBy = disc.getPostedBy().getUsername(); 
        this.discKeywords = disc.getDiscKeywords().stream()
                                .map(DiscKeyword::getValue) 
                                .collect(Collectors.toList());
        this.notified = disc.isNotified();
        this.region = disc.getRegion();
        this.city = disc.getCity();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiscname() {
        return discname;
    }

    public void setDiscname(String discname) {
        this.discname = discname;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region= region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city= city;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public List<String> getDiscKeywords() {
        return discKeywords;
    }

    public void setDiscKeywords(List<String> discKeywords) {
        this.discKeywords = discKeywords;
    }

    public boolean isNotified() {
        return notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }
}
