package com.financialportfolio.backend.external.dto.response;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationDataDto {

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("expiration_date")
    private String expirationDate;

    public AuthenticationDataDto() {
        super();
    }

    public AuthenticationDataDto(String authenticatedUserId, Date tokenExpirationDate) {
        this();
        setUserId(authenticatedUserId);
        setExpirationDate(tokenExpirationDate);
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(expirationDate);
    }

}
