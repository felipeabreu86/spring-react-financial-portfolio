package com.financialportfolio.backend.domain.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.financialportfolio.backend.domain.model.User;
import com.financialportfolio.backend.domain.repository.UserRepository;
import com.financialportfolio.backend.domain.service.UserService;

import io.vavr.control.Either;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Either<UsernameNotFoundException, User> user = userRepository.findBy(email);

        if (user.isLeft()) {
            throw user.getLeft();
        }

        return user.get();
    }

}
