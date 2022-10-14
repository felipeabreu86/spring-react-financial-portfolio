package com.financialportfolio.backend.domain.repository;

import com.financialportfolio.backend.domain.model.PasswordRecoveryToken;

import io.vavr.control.Either;

public interface PasswordRecoveryTokenRepository {

    /**
     * 
     * @param token
     * @return
     */
    public Either<Exception, PasswordRecoveryToken> getPasswordRecoveryTokenBy(final String token);

}
