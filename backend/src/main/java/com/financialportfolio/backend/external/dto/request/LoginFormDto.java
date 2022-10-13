package com.financialportfolio.backend.external.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.financialportfolio.backend.core.util.ConstantsUtil;

public class LoginFormDto {

    @Email(message = "{email.not.valid}", regexp = ConstantsUtil.EMAIL_REGEX_VALIDATION)
    @NotBlank(message = "{email.not.blank}")
    @Size(max = 256, message = "{email.max.size}")
    private String email;

    @NotBlank(message = "{password.not.blank}")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * A partir dos dados da classe LoginFormDto, cria uma instância da classe
     * UsernamePasswordAuthenticationToken que implementa a interface
     * Authentication.
     * 
     * @return Instância contendo os dados de autenticação do usuário.
     */
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(this.email, this.password);
    }

}