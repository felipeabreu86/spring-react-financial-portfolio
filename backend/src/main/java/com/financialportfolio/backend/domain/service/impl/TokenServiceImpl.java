package com.financialportfolio.backend.domain.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.financialportfolio.backend.core.dto.internal.UserDataDto;
import com.financialportfolio.backend.core.util.JsonUtil;
import com.financialportfolio.backend.domain.model.User;
import com.financialportfolio.backend.domain.service.TokenService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenServiceImpl implements TokenService {

    /**
     * Tempo de vida útil do Token do tipo Bearer em milisegundos.
     */
    @Value("${api.jwt.expiration}")
    private String expiration;

    /**
     * Chave privada a ser utilizada para assinar digitalmente o JWT.
     */
    @Value("${api.jwt.secret}")
    private String secret;

    @Override
    public Optional<String> createToken(Authentication authentication) {
        
        User user = (User) authentication.getPrincipal();        
        Date currentDate = new Date();
        Date expirationDate = generateExpirationDate();
        
        String token = Jwts
                .builder()
                .setIssuer("Financial Portfolio API")
                .setSubject(JsonUtil.toJson(new UserDataDto(user)).get())
                .setIssuedAt(currentDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        
        return StringUtils.hasText(token) 
                ? Optional.of(token)
                : Optional.empty();
    }

    @Override
    public boolean isTokenValid(String token) {
        
        boolean isValid = true;
        
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
        } catch (Exception e) {
            isValid = false;
        }
        
        return isValid;
    }
    
    @Override
    public Optional<UserDataDto> getUserDataBy(String token) {

        Optional<UserDataDto> userData = Optional.empty();

        try {
            String subject = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody().getSubject();

            if (StringUtils.hasText(subject)) {
                userData = JsonUtil.readValue(subject, UserDataDto.class);
            }
        } catch (Exception e) { }

        return userData;
    }
    
    @Override
    public Optional<Date> getExpirationDateBy(HttpServletRequest request) {

        Optional<String> token = getBearerTokenBy(request);

        return token.isPresent() 
                ? getExpirationDateBy(token.get()) 
                : Optional.empty();
    }
    
    @Override
    public Optional<Date> getExpirationDateBy(String token) {
        
        Optional<Date> expirationDate = Optional.empty();

        try {
            expirationDate = Optional
                    .ofNullable(Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody().getExpiration());
        } catch (Exception e) { }

        return expirationDate;
    }
    
    @Override
    public Optional<String> getBearerTokenBy(HttpServletRequest request) {
        
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        boolean tokenIsValid = !(token == null || token.isEmpty() || token.length() < 8 || !token.startsWith("Bearer "));

        return tokenIsValid 
                ? Optional.of(token.substring(7, token.length()))
                : Optional.empty();
    }

    /**
     * Recupera a data e hora atual e adiciona a quantidade de tempo (em
     * milisegundos) definida para expiração do token.
     * 
     * @return data e hora de expiração do Token.
     */
    private Date generateExpirationDate() {

        Calendar c = Calendar.getInstance();
        c.add(Calendar.MILLISECOND, Integer.parseInt(expiration));

        return c.getTime();
    }
    
}
