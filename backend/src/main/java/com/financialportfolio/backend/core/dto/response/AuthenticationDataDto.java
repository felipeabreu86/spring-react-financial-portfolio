package com.financialportfolio.backend.core.dto.response;

import java.util.Date;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationDataDto {

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("expiration_date")
    private Date expirationDate;

    public AuthenticationDataDto() {
        super();
    }

    public AuthenticationDataDto(String authenticatedUserId, Optional<Date> tokenExpirationDate) {
        this();

        setUserId(authenticatedUserId);

        if (tokenExpirationDate.isPresent()) {
            setExpirationDate(tokenExpirationDate.get());
        }
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

}
