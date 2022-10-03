package com.financialportfolio.backend.external;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.financialportfolio.backend.external.dto.response.ApiErrorDto;

public interface ApiResponse {

    /**
     * 
     * @param status
     * @param obj
     * @return
     */
    default ResponseEntity<Object> ApiSuccessResponse(HttpStatus status, Object obj) {

        return new ResponseEntity<>(obj, status);
    }

    /**
     * 
     * @param status
     * @param e
     * @return
     */
    default ResponseEntity<Object> ApiErrorResponse(HttpStatus status, Exception e) {

        return this.ApiErrorResponse(status, e.getLocalizedMessage());
    }

    /**
     * 
     * @param status
     * @param error
     * @return
     */
    default ResponseEntity<Object> ApiErrorResponse(HttpStatus status, String error) {

        final ApiErrorDto apiError = new ApiErrorDto(status, error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

}
