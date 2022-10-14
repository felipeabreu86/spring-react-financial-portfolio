package com.financialportfolio.backend.external.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.financialportfolio.backend.core.util.ConstantsUtil;

public class RecoveryPasswordDto {

    @JsonProperty(ConstantsUtil.EMAIL)
    @NotBlank(message = "{email.not.blank}")
    @Size(max = 256, message = "{email.max.size}")
    @Email(message = "{email.not.valid}", regexp = ConstantsUtil.EMAIL_REGEX_VALIDATION)
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
