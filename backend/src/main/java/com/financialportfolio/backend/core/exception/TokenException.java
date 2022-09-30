package com.financialportfolio.backend.core.exception;

public class TokenException extends Exception {

    private static final long serialVersionUID = 1L;

    public TokenException(String errorMessage) {
        super(errorMessage);
    }

    public TokenException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

}
