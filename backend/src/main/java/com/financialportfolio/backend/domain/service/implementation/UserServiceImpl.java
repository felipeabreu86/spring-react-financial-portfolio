package com.financialportfolio.backend.domain.service.implementation;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.financialportfolio.backend.core.exception.UserNotFoundException;
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

        Either<UsernameNotFoundException, User> user = this.findUserBy(email);

        if (user.isLeft()) {
            throw user.getLeft();
        }

        return user.get();
    }

    @Override
    public Either<UsernameNotFoundException, User> findUserBy(final String email) {
        
        return userRepository.findBy(email);
    }
    
    @Override
    public Either<UserNotFoundException, User> findUserBy(Long id) {

        return userRepository.findBy(id);
    }

    @Override
    @Transactional
    public Either<Exception, User> createNew(final User user) {

        if (user == null) {
            return Either.left(new IllegalArgumentException("Dados inválidos!"));
        }

        if (findUserBy(user.getUsername()).isRight()) {
            return Either.left(new Exception("Conta já cadastrada."));
        }

        return userRepository.saveOrUpdate(user);
    }

    @Override
    @Transactional
    public Either<Exception, User> saveOrUpdate(User user) {

        if (user == null) {
            return Either.left(new IllegalArgumentException("Dados inválidos!"));
        }

        return userRepository.saveOrUpdate(user);
    }

    @Override
    @Transactional
    public Either<Exception, Integer> delete(User user) {

        if (user == null) {
            return Either.left(new IllegalArgumentException("Dados inválidos!"));
        }

        return userRepository.delete(user);
    }

}
