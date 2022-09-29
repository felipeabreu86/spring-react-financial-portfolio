package com.financialportfolio.backend.domain.service;

import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

import com.financialportfolio.backend.core.dto.internal.UserDataDto;

public interface TokenService {

    /**
     * Gera um Token do tipo Bearer.
     * 
     * @param authentication - instância de Authentication que contêm os dados do
     *                       usuário a ser autenticado.
     * @return Token do tipo Bearer ou vazio (Optinal.empty()).
     */
    Optional<String> createToken(Authentication authentication);

    /**
     * Verifica se o token passado por parâmetro é válido.
     * 
     * @param token - Token do tipo Bearer.
     * @return True ou False
     */
    boolean isTokenValid(String token);

    /**
     * Retorna os dados do usuário recuperados por meio do Token passado por
     * parâmetro.
     * 
     * @param token - Token do tipo Bearer.
     * @return Dados do usuário ou vazio (Optinal.empty()).
     */
    Optional<UserDataDto> getUserDataBy(String token);

    /**
     * Obtém o Token por meio do parâmetro de cabeçalho (header) "Authorization" da
     * requisição HTTP e retorna a data de expiração deste.
     * 
     * @param httpRequest - requisição HTTP.
     * @return Data de expiração do Token ou vazio (Optinal.empty()).
     */
    Optional<Date> getExpirationDateBy(HttpServletRequest httpRequest);

    /**
     * Retorna a data de expiração do Token passado por parâmetro.
     * 
     * @param token - Token do tipo Bearer.
     * @return Data de expiração do Token ou vazio (Optinal.empty()).
     */
    Optional<Date> getExpirationDateBy(String token);

    /**
     * Obtém o Token por meio do parâmetro de cabeçalho (header) "Authorization" da
     * requisição HTTP.
     * 
     * @param httpRequest - requisição HTTP.
     * @return Token do tipo Bearer ou vazio (Optinal.empty()).
     */
    Optional<String> getBearerTokenBy(HttpServletRequest httpRequest);

}
