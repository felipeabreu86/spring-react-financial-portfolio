package com.financialportfolio.backend.external.dto.response;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ApiErrorDto {

    @JsonInclude(Include.NON_NULL)
    private final HttpStatus status;

    @JsonInclude(Include.NON_NULL)
    private final List<String> errors;

    private final String timestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());

    public ApiErrorDto(HttpStatus status, List<String> errors) {
        super();
        this.status = status;
        this.errors = errors;
    }

    public ApiErrorDto(HttpStatus status, String error) {
        super();
        this.status = status;
        errors = Arrays.asList(error);
    }

    public ApiErrorDto(HttpStatus status, Exception e) {
        this(status, e.getLocalizedMessage());
    }

    public HttpStatus getStatus() {
        return status;
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getTimestamp() {
        return timestamp;
    }

}
