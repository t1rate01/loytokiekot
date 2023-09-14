package com.example.backend.keywords.dto;

import jakarta.validation.constraints.NotNull;


public class UserKeyWordDto {

    private Long id;
    
    @NotNull(message = "Keyword cannot be empty")
    private String keyword;

    public UserKeyWordDto() {
    }

    public UserKeyWordDto(String keyword, Long id) {
        this.keyword = keyword;
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id= id;
    }

    public UserKeyWordDto id(Long id) {
        setId(id);
        return this;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public UserKeyWordDto keyword(String keyword) {
        setKeyword(keyword);
        return this;
    }
    
}
