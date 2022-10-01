package com.financialportfolio.backend.external.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginFormDto {

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

    public UsernamePasswordAuthenticationToken toAuthenticationObject() {
        return new UsernamePasswordAuthenticationToken(this.email, this.password);
    }

}