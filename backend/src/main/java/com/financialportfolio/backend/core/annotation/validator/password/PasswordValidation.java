package com.financialportfolio.backend.core.annotation.validator.password;

public interface PasswordValidation {

    /**
     * Realiza uma validacão associada a senha do usuário.
     * 
     * @param password - senha do usuário.
     * @return True caso a validação tenha sucesso e False caso contrário.
     */
    Boolean isValid(String password);

    /**
     * Mensagem de erro.
     * 
     * @return Texto com a mensagem de erro.
     */
    String getErrorMessage();

}
