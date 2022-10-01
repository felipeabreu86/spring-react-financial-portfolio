package com.financialportfolio.backend.mock;

public class TokenMock {

    private String access_token;
    private String token_type;

    public TokenMock() {
        super();
    }

    public TokenMock(String access_token, String token_type) {
        this();
        this.access_token = access_token;
        this.token_type = token_type;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getToken_type() {
        return token_type;
    }

}
