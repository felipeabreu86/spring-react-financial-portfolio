package com.financialportfolio.backend.core.filter;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.financialportfolio.backend.core.dto.internal.UserDataDto;
import com.financialportfolio.backend.domain.service.TokenService;

public class BearerTokenFilter extends OncePerRequestFilter {

    /**
     * Regras de negócio relacionadas ao Token.
     */
    private TokenService tokenService;

    public BearerTokenFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, 
            HttpServletResponse response, 
            FilterChain filterChain) throws ServletException, IOException {

        Optional<String> token = tokenService.getBearerTokenBy(request);

        if (token.isPresent() && tokenService.isTokenValid(token.get())) {
            authenticateUser(token.get());
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Método responsável por alterar o usuário atualmente autenticado ou remover as
     * informações de autenticação.
     * 
     * @param token - Token do tipo Bearer.
     */
    private void authenticateUser(String token) {

        Optional<UserDataDto> userData = tokenService.getUserDataBy(token);
        
        if (userData.isPresent()) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userData.get().getUserId(),
                    null, 
                    userData.get().getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

}
