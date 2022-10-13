package com.financialportfolio.backend.external;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.financialportfolio.backend.external.dto.response.ApiErrorDto;

public interface ApiResponse {

    /**
     * Cria uma resposta de sucesso a ser retornada pela API.
     * 
     * @param status - HTTP Status de sucesso, por exemplo, 200.
     * @param obj    - objeto a ser retornado como corpo da resposta.
     * @return ResponseEntity.
     */
    default ResponseEntity<?> ApiSuccessResponse(HttpStatus status, Object obj) {

        return new ResponseEntity<>(obj, status);
    }

    /**
     * Cria uma resposta de erro a ser retornada pela API.
     * 
     * @param status - HTTP Status de sucesso, por exemplo, 404.
     * @param e      - Exceção.
     * @return ResponseEntity.
     */
    default ResponseEntity<?> ApiErrorResponse(HttpStatus status, Exception e) {

        return this.ApiErrorResponse(status, e.getLocalizedMessage());
    }

    /**
     * Cria uma resposta de erro a ser retornada pela API.
     * 
     * @param status - HTTP Status de sucesso, por exemplo, 404.
     * @param error  - Descrição do erro em texto.
     * @return ResponseEntity.
     */
    default ResponseEntity<?> ApiErrorResponse(HttpStatus status, String error) {

        final ApiErrorDto apiError = new ApiErrorDto(status, error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

}
