package com.financialportfolio.backend.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financialportfolio.backend.domain.model.User;

@Repository
public interface UsuarioRepository extends JpaRepository<User, Long> {

    /**
     * Realiza a busca por usuário no banco de dados filtrando pelo email.
     * 
     * @param email - email do usuário.
     * @return Usuário encontrado no banco de dados ou vazio (Optional.empty()).
     */
    Optional<User> findByEmail(String email);

}
