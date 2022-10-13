package com.financialportfolio.backend.core.annotation.validator.password;

import org.springframework.util.StringUtils;

public class PasswordLowerCaseValidation implements PasswordValidation {

    @Override
    public Boolean isValid(String password) {
        return this.containsLowerCase(password);
    }

    @Override
    public String getErrorMessage() {
        return "{password.lower.case}";
    }

    /**
     * Verifica se o texto contém ao menos uma letra minúscula.
     * 
     * @param value - texto
     * @return true ou false
     */
    private boolean containsLowerCase(String value) {
        
        return StringUtils.hasText(value) 
                ? value.chars().filter(Character::isLowerCase).findAny().isPresent() 
                : false;
    }

}
