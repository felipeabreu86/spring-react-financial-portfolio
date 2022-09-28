package com.financialportfolio.backend.dto;

public class ErrorDto {

    private String field;

    private String error;

    public ErrorDto(String field, String error) {
        super();
        this.field = field;
        this.error = error;
    }

    public String getCampo() {
        return field;
    }

    public String getErro() {
        return error;
    }

}
