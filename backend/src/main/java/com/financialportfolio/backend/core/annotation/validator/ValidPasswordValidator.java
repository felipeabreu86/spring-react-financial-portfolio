package com.financialportfolio.backend.core.annotation.validator;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.financialportfolio.backend.core.annotation.ValidPassword;
import com.financialportfolio.backend.core.annotation.validator.password.PasswordLengthValidation;
import com.financialportfolio.backend.core.annotation.validator.password.PasswordLowerCaseValidation;
import com.financialportfolio.backend.core.annotation.validator.password.PasswordSpecialCharacterValidation;
import com.financialportfolio.backend.core.annotation.validator.password.PasswordUpperCaseValidation;
import com.financialportfolio.backend.core.annotation.validator.password.PasswordValidation;

public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private List<PasswordValidation> passwordValidations = this.getValidations();

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        Boolean isValid = true;
        
        for (PasswordValidation passwordValidation : passwordValidations) {
            if (!passwordValidation.isValid(password)) {
                isValid = false;
                
                context
                    .buildConstraintViolationWithTemplate(passwordValidation.getErrorMessage())
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            }
        }
        
        return isValid;
    }
    
    /**
     * Responsável por criar a lista de validações da senha do usuário.
     * 
     * @return lista de validações da senha do usuário.
     */
    private List<PasswordValidation> getValidations() {

        List<PasswordValidation> validations = new ArrayList<PasswordValidation>();
        validations.add(new PasswordLengthValidation());
        validations.add(new PasswordUpperCaseValidation());
        validations.add(new PasswordLowerCaseValidation());
        validations.add(new PasswordSpecialCharacterValidation());

        return validations;
    }

}
