package com.financialportfolio.backend.domain.repository;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.financialportfolio.backend.core.exception.UserNotFoundException;
import com.financialportfolio.backend.domain.model.User;

import io.vavr.control.Either;

public interface UserRepository {

    /**
     * 
     * @param id
     * @return
     */
    Either<UserNotFoundException, User> findBy(Long id);

    /**
     * 
     * @param email
     * @return
     */
    Either<UsernameNotFoundException, User> findBy(String email);

    /**
     * 
     * @param user
     * @return
     */
    Either<Exception, User> saveOrUpdate(User user);

}
