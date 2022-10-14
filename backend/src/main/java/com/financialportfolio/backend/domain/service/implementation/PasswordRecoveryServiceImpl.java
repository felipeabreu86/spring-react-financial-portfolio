package com.financialportfolio.backend.domain.service.implementation;

import java.util.Calendar;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.financialportfolio.backend.domain.model.PasswordRecoveryToken;
import com.financialportfolio.backend.domain.model.User;
import com.financialportfolio.backend.domain.repository.PasswordRecoveryTokenRepository;
import com.financialportfolio.backend.domain.repository.UserRepository;
import com.financialportfolio.backend.domain.service.PasswordRecoveryService;

import io.vavr.control.Either;

@Service
public class PasswordRecoveryServiceImpl implements PasswordRecoveryService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordRecoveryTokenRepository passwordRecoveryTokenRepository;

    @Override
    public Either<Exception, PasswordRecoveryToken> createPasswordRecoveryToken(User user) {

        PasswordRecoveryToken token = createNewPasswordRecoveryToken(user);
        user.addPasswordRecoveryToken(token);
  
        return userRepository.saveOrUpdate(user).isRight() 
                ? Either.right(token)
                : Either.left(new Exception("Erro ao salvar o Token"));
    }
    
    @Override
    public Either<Exception, PasswordRecoveryToken> getPasswordRecoveryToken(String email, String token) {

        if (!StringUtils.hasText(email) || !StringUtils.hasText(token)) {
            return Either.left(new IllegalArgumentException("Usuário ou Token inválidos."));
        }

        Either<Exception, PasswordRecoveryToken> tokenResult = passwordRecoveryTokenRepository.getPasswordRecoveryTokenBy(token);

        if (tokenResult.isLeft()) {
            return Either.left(tokenResult.getLeft());
        }

        if (tokenResult.get().getExpiryDate().before(Calendar.getInstance().getTime())) {
            return Either.left(new Exception("Token expirado."));
        }

        if (!tokenResult.get().getUser().getUsername().equals(email)) {
            return Either.left(new Exception("Token inválido."));
        }

        return tokenResult;
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
