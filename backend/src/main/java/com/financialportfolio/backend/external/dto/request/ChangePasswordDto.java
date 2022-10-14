package com.financialportfolio.backend.external.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.financialportfolio.backend.core.annotation.ValidPassword;
import com.financialportfolio.backend.core.util.ConstantsUtil;

public class ChangePasswordDto {

    @JsonProperty(ConstantsUtil.EMAIL)
    @Email(message = "{email.not.valid}", regexp = ConstantsUtil.EMAIL_REGEX_VALIDATION)
    @NotBlank(message = "{email.not.blank}")
    @Size(max = 256, message = "{email.max.size}")
    private String email;

    @JsonProperty(ConstantsUtil.TOKEN)
    @NotBlank(message = "{token.not.blank}")
    private String token;

    @NotBlank(message = "{password.not.blank}")
    @ValidPassword
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
