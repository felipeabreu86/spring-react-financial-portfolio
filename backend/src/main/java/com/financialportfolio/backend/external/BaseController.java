package com.financialportfolio.backend.external;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.financialportfolio.backend.external.dto.response.ApiErrorDto;

public interface BaseController {

    default ResponseEntity<ApiErrorDto> buildApiErrorResponse(HttpStatus status, Exception e) {

        final ApiErrorDto apiError = new ApiErrorDto(status, e);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

}
