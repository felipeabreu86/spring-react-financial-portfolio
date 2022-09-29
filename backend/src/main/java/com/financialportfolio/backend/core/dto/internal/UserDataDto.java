package com.financialportfolio.backend.core.dto.internal;

import java.util.ArrayList;
import java.util.Collection;

import com.financialportfolio.backend.core.util.CastUtils;
import com.financialportfolio.backend.domain.model.Authority;
import com.financialportfolio.backend.domain.model.User;

public class UserDataDto {

    private Long userId;

    private Collection<Authority> authorities = new ArrayList<>();

    public UserDataDto() {
        super();
    }

    public UserDataDto(User user) {
        this.userId = user.getId();
        this.authorities = CastUtils.castList(Authority.class, user.getAuthorities());
    }

    public Long getUserId() {
        return this.userId;
    }

    public Collection<Authority> getAuthorities() {
        return this.authorities;
    }

}
