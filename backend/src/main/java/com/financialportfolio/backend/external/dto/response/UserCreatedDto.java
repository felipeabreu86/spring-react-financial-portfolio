package com.financialportfolio.backend.external.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserCreatedDto {

    @JsonProperty("user_id")
    private Long userId;

    public UserCreatedDto() {
        super();
    }

    public UserCreatedDto(Long userId) {
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
