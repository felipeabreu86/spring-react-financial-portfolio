package com.financialportfolio.backend.domain.repository;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.financialportfolio.backend.core.exception.UserNotFoundException;
import com.financialportfolio.backend.domain.model.User;

import io.vavr.control.Either;

public interface UserRepository {

    Either<UserNotFoundException, User> findBy(Long id);
    
    Either<UsernameNotFoundException, User> findBy(String email);

}
