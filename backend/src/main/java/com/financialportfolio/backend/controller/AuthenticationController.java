package com.financialportfolio.backend.controller;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financialportfolio.backend.core.dto.request.LoginFormDto;
import com.financialportfolio.backend.core.dto.response.AuthenticationDataDto;
import com.financialportfolio.backend.core.dto.response.TokenDto;
import com.financialportfolio.backend.domain.service.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    /**
     * Realiza a autenticação do usuário e retorna um JSON informando o Token e o
     * seu tipo (Bearer).
     * 
     * @param loginDto - dados do usuário a ser autenticado.
     * @return instância de TokenDto.
     */
    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE, 
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenDto> createToken(@RequestBody @Valid LoginFormDto loginFormDto) {

        ResponseEntity<TokenDto> response = null;

        try {
            Optional<String> token = tokenService
                    .createToken(authManager.authenticate(loginFormDto.toAuthenticationObject()));
            
            response = token.isPresent() 
                    ? ResponseEntity.ok(new TokenDto(token.get(), "Bearer"))
                    : ResponseEntity.badRequest().build();            
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            response = ResponseEntity.badRequest().build();
        } catch (Exception e) {
            response = ResponseEntity.internalServerError().build();
        }

        return response;
    }

    /**
     * Retorna um JSON indicando o usuário autenticado e a data de expiração do
     * Token.
     * 
     * @param request   - requisição HTTP.
     * @param principal - entidade que representa o usuário autenticado.
     * @return instância de DadosAutenticacaoDto.
     */
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE, 
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationDataDto> checkAuthentication(HttpServletRequest request, Principal principal) {

        return ResponseEntity.ok(
                new AuthenticationDataDto(principal.getName(), tokenService.getExpirationDateBy(request)));
    }

}
