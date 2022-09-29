package com.financialportfolio.backend.domain.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.financialportfolio.backend.domain.model.User;
import com.financialportfolio.backend.domain.service.UsuarioService;
import com.financialportfolio.backend.infrastructure.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optUser = userRepository.findByEmail(username);

        if (!optUser.isPresent()) {
            throw new UsernameNotFoundException(username);
        }

        return optUser.get();
    }

}
