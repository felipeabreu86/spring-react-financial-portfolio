package com.financialportfolio.backend.infrastructure.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.financialportfolio.backend.domain.model.PasswordRecoveryToken;
import com.financialportfolio.backend.domain.repository.PasswordRecoveryTokenRepository;
import com.financialportfolio.backend.infrastructure.dao.PasswordRecoveryTokenDao;

import io.vavr.control.Either;

@Repository
public class PasswordRecoveryTokenRepositoryImpl implements PasswordRecoveryTokenRepository {
    
    @Autowired
    private PasswordRecoveryTokenDao passwordRecoveryTokenDao;

    @Override
    public Either<Exception, PasswordRecoveryToken> getPasswordRecoveryTokenBy(String token) {

        Optional<PasswordRecoveryToken> opt = passwordRecoveryTokenDao.findByToken(token);

        return opt.isPresent() 
                ? Either.right(opt.get()) 
                : Either.left(new Exception("Token não encontrado."));
    }

}
