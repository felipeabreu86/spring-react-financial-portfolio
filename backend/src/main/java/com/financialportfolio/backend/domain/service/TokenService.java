package com.financialportfolio.backend.domain.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

import com.financialportfolio.backend.core.exception.TokenException;
import com.financialportfolio.backend.domain.service.dto.UserDataDto;

import io.vavr.control.Either;

public interface TokenService {

    /**
     * Verifica se o token passado por parâmetro é válido.
     * 
     * @param token - Token do tipo Bearer.
     * @return Retorna se o Token é válido ou inválido.
     */
    boolean isTokenValid(String token);

    /**
     * Gera um Token do tipo Bearer.
     * 
     * @param authentication - instância de Authentication que contêm os dados do
     *                       usuário a ser autenticado.
     * @return Token do tipo Bearer.
     */
    Either<TokenException, String> createToken(Authentication authentication);

    /**
     * Retorna os dados do usuário recuperados por meio do Token passado por
     * parâmetro.
     * 
     * @param token - Token do tipo Bearer.
     * @return Dados do usuário recuperados por meio do Token.
     */
    Either<TokenException, UserDataDto> getUserDataBy(String token);

    /**
     * Obtém o Token do tipo Bearer por meio do parâmetro de cabeçalho/header
     * "Authorization" da requisição HTTP e retorna a data de expiração deste.
     * 
     * @param httpRequest - requisição HTTP.
     * @return Data de expiração do Token.
     */
    Either<TokenException, Date> getExpirationDateBy(HttpServletRequest httpRequest);

    /**
     * Retorna a data de expiração do Token passado por parâmetro.
     * 
     * @param token - Token do tipo Bearer.
     * @return Data de expiração do Token.
     */
    Either<TokenException, Date> getExpirationDateBy(String token);

    /**
     * Obtém o Token do tipo Bearer por meio do parâmetro de cabeçalho/header
     * "Authorization" da requisição HTTP.
     * 
     * @param httpRequest - requisição HTTP.
     * @return Token do tipo Bearer.
     */
    Either<TokenException, String> getBearerTokenBy(HttpServletRequest httpRequest);

}
