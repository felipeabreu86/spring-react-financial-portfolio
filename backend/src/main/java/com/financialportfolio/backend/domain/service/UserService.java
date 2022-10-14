package com.financialportfolio.backend.domain.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.financialportfolio.backend.core.exception.UserNotFoundException;
import com.financialportfolio.backend.domain.model.User;

import io.vavr.control.Either;

public interface UserService extends UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

    /**
     * Realiza a busca do usuário pelo email.
     * 
     * @param email - email do usuário.
     * @return Usuário.
     */
    Either<UsernameNotFoundException, User> findUserBy(String email);

    /**
     * Realiza a busca do usuário pelo id do usuário.
     * 
     * @param id - identificador do usuário.
     * @return Usuário.
     */
    Either<UserNotFoundException, User> findUserBy(Long id);

    /**
     * Cria um novo Usuário e o salva no banco de dados.
     * 
     * @param user - Usuário.
     * @return Usuário.
     */
    Either<Exception, User> createNew(User user);

    /**
     * Salva ou atualiza o usuário no banco de dados.
     * 
     * @param user - usuário.
     * @return Usuário.
     */
    Either<Exception, User> saveOrUpdate(User user);

    /**
     * Remove o usuário do banco de dados.
     * 
     * @param user - usuário.
     * @return quantidade de registros removidos do banco de dados.
     */
    Either<Exception, Integer> delete(User user);

}
