package com.financialportfolio.backend.core.exception;

public class InvalidUrlException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidUrlException(String errorMessage) {
        super(errorMessage);
    }

    public InvalidUrlException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

}
