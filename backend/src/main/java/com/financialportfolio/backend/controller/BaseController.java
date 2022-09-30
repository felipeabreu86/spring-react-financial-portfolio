package com.financialportfolio.backend.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.financialportfolio.backend.core.dto.response.ApiErrorDto;

public interface BaseController {

    default ResponseEntity<ApiErrorDto> buildApiErrorResponse(HttpStatus status, Exception e) {

        final ApiErrorDto apiError = new ApiErrorDto(status, e);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

}
