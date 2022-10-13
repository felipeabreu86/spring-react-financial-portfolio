package com.financialportfolio.backend.core.annotation.validator.password;

public class PasswordLengthValidation implements PasswordValidation {

    @Override
    public Boolean isValid(String password) {

        int tamanho = password.length();

        return (tamanho < 8 || tamanho > 16) 
                ? false 
                : true;
    }

    @Override
    public String getErrorMessage() {
        return "{password.length}";
    }

}
