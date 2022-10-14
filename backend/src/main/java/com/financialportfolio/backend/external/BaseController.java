package com.financialportfolio.backend.external;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.financialportfolio.backend.domain.model.type.AuthorityType;

public interface BaseController extends ApiResponse {

    /**
     * Verifica se o usuário autenticado apresenta o papel de administrador.
     * 
     * @return retorna se o usuário autenticado é administrador ou não.
     */
    default boolean isAdmin() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(AuthorityType.ROLE_ADMIN.toString()))) {
            return true;
        }
        return false;
    }

}
