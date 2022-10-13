package com.financialportfolio.backend.domain.service;

import com.financialportfolio.backend.domain.model.PasswordRecoveryToken;
import com.financialportfolio.backend.domain.model.User;

import io.vavr.control.Either;

public interface PasswordRecoveryService {

    /**
     * Retorna e salva no banco de dados um token de autenticação para o usuário,
     * que pode ser usado, por exemplo, na recuperação de senha.
     * 
     * @param user - Usuário.
     * @return instância de uma entidade que representa um token de autenticação
     *         relacionado a um Usuário.
     */
    public Either<Exception, PasswordRecoveryToken> createPasswordRecoveryToken(User user);

}
