package com.financialportfolio.backend.external.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDataDto {

    @JsonProperty("user_id")
    private Long userId;

    public UserDataDto() {
        super();
    }

    public UserDataDto(Long userId) {
        this();
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
