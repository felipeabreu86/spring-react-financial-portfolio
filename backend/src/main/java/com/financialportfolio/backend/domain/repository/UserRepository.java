package com.financialportfolio.backend.domain.repository;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.financialportfolio.backend.core.exception.UserNotFoundException;
import com.financialportfolio.backend.domain.model.User;

import io.vavr.control.Either;

public interface UserRepository {

    /**
     * Realiza a busca do usuário pelo identificador único (id).
     * 
     * @param id - identificador único do usuário.
     * @return Usuário.
     */
    Either<UserNotFoundException, User> findBy(Long id);

    /**
     * Realiza a busca do usuário pelo email.
     * 
     * @param email - email do usuário.
     * @return Usuário.
     */
    Either<UsernameNotFoundException, User> findBy(String email);

    /**
     * Salva um novo usuário no banco de dados ou atualiza um existente.
     * 
     * @param user - Usuário a ser salvo ou atualizado.
     * @return Usuário.
     */
    Either<Exception, User> saveOrUpdate(User user);

}
