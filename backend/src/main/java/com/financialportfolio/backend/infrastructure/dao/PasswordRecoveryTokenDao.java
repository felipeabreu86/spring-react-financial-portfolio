package com.financialportfolio.backend.infrastructure.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financialportfolio.backend.domain.model.PasswordRecoveryToken;

@Repository
public interface PasswordRecoveryTokenDao extends JpaRepository<PasswordRecoveryToken, Long> {

    Optional<PasswordRecoveryToken> findByToken(String token);

}
