package com.financialportfolio.backend.domain.service;

import javax.servlet.http.HttpServletRequest;

import com.financialportfolio.backend.domain.model.User;

import io.vavr.control.Either;

public interface EmailService {

    /**
     * Envia um email de recuperação de senha pro endereço eletrônico cadastrado
     * para o usuário. No corpo do email é enviada uma URL para reconfiguração da
     * senha que contém um Token de autenticação.
     * 
     * @param request - HTTP request.
     * @param token   - Token de autenticação do usuário.
     * @param user    - Usuário.
     * @return Retorna 1 indicando que um email foi enviado com sucesso.
     */
    Either<Exception, Integer> sendPasswordRecoveryTokenEmail(
            final HttpServletRequest request, 
            final String token,
            final User user);

}
