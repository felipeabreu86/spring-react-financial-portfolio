package com.financialportfolio.backend.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class UserDataDto {

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public Object getEmail() {
        return null;
    }

}
