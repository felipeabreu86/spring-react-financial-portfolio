package com.financialportfolio.backend.domain.service;

import com.financialportfolio.backend.domain.model.PasswordRecoveryToken;
import com.financialportfolio.backend.domain.model.User;

import io.vavr.control.Either;

public interface PasswordRecoveryService {

    /**
     * Retorna e salva no banco de dados um token de autenticação para o usuário,
     * que pode ser usado, por exemplo, na recuperação de senha.
     * 
     * @param user - usuário.
     * @return instância de PasswordRecoveryToken.
     */
    public Either<Exception, PasswordRecoveryToken> createPasswordRecoveryToken(User user);

    /**
     * Verifica se o Token é válido e se está vinculado ao usuário do email passado
     * por parâmetro. Se o Token for válido, é retornada uma instância da classe
     * PasswordRecoveryToken.
     * 
     * @param email - email do usuário.
     * @param token - Token de recuperação de senha.
     * @return instância de PasswordRecoveryToken.
     */
    public Either<Exception, PasswordRecoveryToken> getPasswordRecoveryToken(
            final String email, 
            final String token);

}
