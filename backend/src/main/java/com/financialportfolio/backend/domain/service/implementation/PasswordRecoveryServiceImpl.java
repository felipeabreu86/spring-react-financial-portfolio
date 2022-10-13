package com.financialportfolio.backend.domain.service.implementation;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.financialportfolio.backend.domain.model.PasswordRecoveryToken;
import com.financialportfolio.backend.domain.model.User;
import com.financialportfolio.backend.domain.repository.UserRepository;
import com.financialportfolio.backend.domain.service.PasswordRecoveryService;

import io.vavr.control.Either;

@Service
public class PasswordRecoveryServiceImpl implements PasswordRecoveryService {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public Either<Exception, PasswordRecoveryToken> createPasswordRecoveryToken(User user) {

        PasswordRecoveryToken token = createNewPasswordRecoveryToken(user);
        user.addPasswordRecoveryToken(token);
  
        return userRepository.saveOrUpdate(user).isRight() 
                ? Either.right(token)
                : Either.left(new Exception("Erro ao salvar o Token"));
    }
    
    /**
     * Cria um novo token de recuperação de senha.
     * 
     * @param user - Usuário.
     * @return instância de uma entidade que representa um token de autenticação em
     *         relacionado a um Usuário.
     */
    private PasswordRecoveryToken createNewPasswordRecoveryToken(User user) {

        return new PasswordRecoveryToken(UUID.randomUUID().toString(), user);
    }

}
