package com.financialportfolio.backend.core.annotation.validator.password;

import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

public class PasswordSpecialCharacterValidation implements PasswordValidation {

    @Override
    public Boolean isValid(String password) {
        return this.containsSpecialChar(password);
    }

    @Override
    public String getErrorMessage() {
        return "{password.special.character}";
    }

    /**
     * Verifica se o texto contém ao menos um caracter especial.
     * 
     * @param value - texto
     * @return true ou false
     */
    private boolean containsSpecialChar(String value) {

        return StringUtils.hasText(value) 
                ? Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]").matcher(value).find() 
                : false;
    }

}
