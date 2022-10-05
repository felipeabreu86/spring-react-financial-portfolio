package com.financialportfolio.backend.domain.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.financialportfolio.backend.domain.model.User;

import io.vavr.control.Either;

public interface UserService extends UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

    /**
     * 
     * @param email
     * @return
     */
    Either<UsernameNotFoundException, User> findUserBy(String email);

    /**
     * 
     * @param user
     * @return
     */
    Either<Exception, User> createNew(User user);

}
