package com.financialportfolio.backend.external.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financialportfolio.backend.domain.service.TokenService;
import com.financialportfolio.backend.external.BaseController;
import com.financialportfolio.backend.external.dto.request.LoginFormDto;
import com.financialportfolio.backend.external.dto.response.AuthenticationDataDto;
import com.financialportfolio.backend.external.dto.response.TokenDto;

@RestController
@RequestMapping("/auth")
public class AuthenticationController implements BaseController {

    @Autowired  
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    /**
     * Realiza a autenticação do usuário e retorna um JSON informando o Token e o
     * seu tipo (Bearer).
     * 
     * @param loginFormDto - dados do usuário a ser autenticado.
     * @return informações do Token criado.
     */
    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE, 
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createToken(@RequestBody @Valid LoginFormDto loginFormDto) {

        return tokenService
                .createToken(authManager.authenticate(loginFormDto.toAuthentication()))
                .fold(exception -> {
                    return ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception);
                }, token -> {
                    return ApiSuccessResponse(HttpStatus.OK, new TokenDto(token, "Bearer"));
                });
    }

    /**
     * Retorna um JSON indicando o id do usuário autenticado e a data de expiração
     * do Token.
     * 
     * @param request   - requisição HTTP.
     * @param principal - entidade que representa o usuário autenticado.
     * @return informações do usuário autenticado.
     */
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE, 
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> checkAuthentication(HttpServletRequest request, Principal principal) {

        return tokenService
                .getExpirationDateBy(request)
                .fold(exception -> {
                    return ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception);
                }, date -> {
                    return ApiSuccessResponse(HttpStatus.OK, new AuthenticationDataDto(principal.getName(), date));
                });
    }

}
