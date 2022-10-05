package com.financialportfolio.backend.core.annotation.validator.password;

import org.springframework.util.StringUtils;

public class PasswordUpperCaseValidation implements PasswordValidation {

    @Override
    public Boolean isValid(String password) {
        return this.containsUpperCase(password);
    }

    @Override
    public String getErrorMessage() {
        return "{password.upper.case}";
    }

    private boolean containsUpperCase(String password) {
        
        return StringUtils.hasText(password) 
                ? password.chars().filter(Character::isUpperCase).findAny().isPresent() 
                : false;
    }

}
