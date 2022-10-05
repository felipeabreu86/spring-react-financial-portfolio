package com.financialportfolio.backend.core.annotation.validator.password;

public interface PasswordValidation {

    Boolean isValid(String password);

    String getErrorMessage();

}
