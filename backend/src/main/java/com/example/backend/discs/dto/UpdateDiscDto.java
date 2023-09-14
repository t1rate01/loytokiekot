package com.example.backend.discs.dto;

import java.util.List;
import java.util.Optional;
import java.util.Objects;



public class UpdateDiscDto {
    private Optional<String> discname;
    private Optional<List<String>> keywords;
    private Optional<String> region;
    private Optional<String> city;

    public UpdateDiscDto() {
    }

    public UpdateDiscDto(Optional<String> discname, Optional<List<String>> keywords , Optional<String> region, Optional<String> city) {
        this.discname = discname;
        this.keywords = keywords;
        this.region = region;
        this.city = city;
    }

    public Optional<String> getDiscname() {
        return this.discname;
    }

    public void setDiscname(Optional<String> discname) {
        this.discname = discname;
    }

    public Optional<List<String>> getKeywords() {
        return this.keywords;
    }

    public Optional<String> getRegion() {
        return this.region;
    }

    public void setRegion(Optional<String> region) {
        this.region = region;
    }

    public Optional<String> getCity() {
        return this.city;
    }

    public void setCity(Optional<String> city) {
        this.city = city;
    }

    public void setKeywords(Optional<List<String>> keywords) {
        this.keywords = keywords;
    }

    public UpdateDiscDto discname(Optional<String> discname) {
        setDiscname(discname);
        return this;
    }

    public UpdateDiscDto keywords(Optional<List<String>> keywords) {
        setKeywords(keywords);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UpdateDiscDto)) {
            return false;
        }
        UpdateDiscDto updateDiscDto = (UpdateDiscDto) o;
        return Objects.equals(discname, updateDiscDto.discname) && Objects.equals(keywords, updateDiscDto.keywords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discname, keywords);
    }

    @Override
    public String toString() {
        return "{" +
            " discname='" + getDiscname() + "'" +
            ", keywords='" + getKeywords() + "'" +
            "}";
    }
    
}
