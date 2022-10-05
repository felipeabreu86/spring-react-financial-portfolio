package com.financialportfolio.backend.infrastructure.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.financialportfolio.backend.core.exception.UserNotFoundException;
import com.financialportfolio.backend.core.util.MessageUtil;
import com.financialportfolio.backend.domain.model.User;
import com.financialportfolio.backend.domain.repository.UserRepository;
import com.financialportfolio.backend.infrastructure.dao.UserDao;

import io.vavr.control.Either;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MessageUtil messageUtil;

    @Override
    public Either<UserNotFoundException, User> findBy(Long id) {

        Optional<User> user = (id != null && id >= 0)
                ? userDao.findById(id) 
                : Optional.empty();
        
        return user.isPresent()
                ? Either.right(user.get())
                : Either.left(new UserNotFoundException(messageUtil.getMessageBy("error.user.not.found")));
    }
    
    @Override
    public Either<UsernameNotFoundException, User> findBy(String email) {

        Optional<User> user = StringUtils.hasText(email)
                ? userDao.findByEmail(email)
                : Optional.empty();
        
        return user.isPresent()
                ? Either.right(user.get())
                : Either.left(new UsernameNotFoundException(messageUtil.getMessageBy("error.user.not.found")));
    }

    @Override
    public Either<Exception, User> saveOrUpdate(User user) {
        
        try {
            return Either.right(userDao.save(user));
        } catch (Exception e) {
            return Either.left(e);
        }
    }

}
