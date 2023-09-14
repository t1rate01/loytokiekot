package com.example.backend.keywords.dto;

import com.example.backend.keywords.UserKeyword;

public class GetUserKeyWordsDto {

    private Long id;
    private String value;
    private Long userId;

    public GetUserKeyWordsDto(UserKeyword userKeyword) {
        this.id = userKeyword.getId();
        this.value = userKeyword.getValue();
        this.userId = userKeyword.getUser().getId();
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }



}

