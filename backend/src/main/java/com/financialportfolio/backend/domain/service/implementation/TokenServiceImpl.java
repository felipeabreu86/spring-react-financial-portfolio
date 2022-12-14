package com.financialportfolio.backend.domain.service.implementation;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.financialportfolio.backend.core.exception.TokenException;
import com.financialportfolio.backend.core.util.JsonUtil;
import com.financialportfolio.backend.domain.model.User;
import com.financialportfolio.backend.domain.service.TokenService;
import com.financialportfolio.backend.domain.service.dto.UserDataDto;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.vavr.control.Either;

@Service
public class TokenServiceImpl implements TokenService {

    private final Log logger = LogFactory.getLog(getClass());

    /**
     * Tempo de vida útil do Token do tipo Bearer em milisegundos.
     */
    @Value("${api.jwt.expiration}")
    private Integer expiration;

    /**
     * Chave privada a ser utilizada para assinar digitalmente o JWT.
     */
    @Value("${api.jwt.secret}")
    private String secret;

    @Override
    public boolean isTokenValid(String token) {
        
        boolean isValid = true;
        
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
        } catch (Exception e) {
            logger.info(e.getLocalizedMessage());
            isValid = false;
        }
        
        return isValid;
    }

    @Override
    public Either<TokenException, String> createToken(Authentication authentication) {
        
        User user = (User) authentication.getPrincipal();        
        Date currentDate = new Date();
        Date expirationDate = this.generateExpirationDate();
        
        String token = Jwts
                .builder()
                .setIssuer("Financial Portfolio API")
                .setSubject(JsonUtil.toJson(new UserDataDto(user)).get())
                .setIssuedAt(currentDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        
        return StringUtils.hasText(token) 
                ? Either.right(token)
                : Either.left(new TokenException("Erro ao gerar o Token."));
    }
    
    @Override
    public Either<TokenException, UserDataDto> getUserDataBy(String token) {

        Optional<UserDataDto> userData = Optional.empty();

        try {
            String subject = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody().getSubject();

            if (StringUtils.hasText(subject)) {
                userData = JsonUtil.readValue(subject, UserDataDto.class);
            }
        } catch (Exception e) {
            logger.info(e.getLocalizedMessage());
        }

        return userData.isPresent() 
                ? Either.right(userData.get())
                : Either.left(new TokenException("Erro ao obter os dados do usuário."));
    }
    
    @Override
    public Either<TokenException, Date> getExpirationDateBy(HttpServletRequest request) {

        Either<TokenException, String> token = this.getBearerTokenBy(request);

        return token.isRight() 
                ? this.getExpirationDateBy(token.get()) 
                : Either.left(token.getLeft());
    }
    
    @Override
    public Either<TokenException, Date> getExpirationDateBy(String token) {
        
        Optional<Date> expirationDate = Optional.empty();

        try {
            expirationDate = Optional.ofNullable(
                    Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody().getExpiration());
        } catch (Exception e) {
            logger.info(e.getLocalizedMessage());
        }

        return expirationDate.isPresent()
                ? Either.right(expirationDate.get())
                : Either.left(new TokenException("Erro ao obter a data de expiração do Token."));
    }
    
    @Override
    public Either<TokenException, String> getBearerTokenBy(HttpServletRequest request) {
        
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        boolean tokenIsValid = StringUtils.hasText(token) && token.length() > 7 && token.startsWith("Bearer ");

        return tokenIsValid 
                ? Either.right(token.substring(7, token.length()))
                : Either.left(new TokenException("Erro ao obter o Token."));
    }

    /**
     * Recupera a data e hora atual e adiciona a quantidade de tempo (em
     * milisegundos) definida para expiração do token.
     * 
     * @return data e hora de expiração do Token.
     */
    private Date generateExpirationDate() {

        Calendar c = Calendar.getInstance();
        c.add(Calendar.MILLISECOND, expiration);

        return c.getTime();
    }
    
}
