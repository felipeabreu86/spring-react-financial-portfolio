package com.financialportfolio.backend.infrastructure.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financialportfolio.backend.domain.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

    /**
     * Realiza a busca por usuário no banco de dados filtrando pelo email.
     * 
     * @param email - email do usuário.
     * @return Usuário encontrado no banco de dados.
     */
    Optional<User> findByEmail(String email);

}
