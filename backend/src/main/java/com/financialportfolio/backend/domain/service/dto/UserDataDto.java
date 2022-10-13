package com.financialportfolio.backend.domain.service.dto;

import java.util.ArrayList;
import java.util.Collection;

import com.financialportfolio.backend.core.util.CastUtil;
import com.financialportfolio.backend.domain.model.Authority;
import com.financialportfolio.backend.domain.model.User;

public class UserDataDto {

    private Long userId;

    private Collection<Authority> authorities = new ArrayList<>();

    // Construtores

    public UserDataDto() {
        super();
    }

    public UserDataDto(User user) {
        this.userId = user.getId();
        this.authorities = CastUtil.castList(Authority.class, user.getAuthorities());
    }

    // Getters e Setters

    public Long getUserId() {
        return this.userId;
    }

    public Collection<Authority> getAuthorities() {
        return this.authorities;
    }

}
