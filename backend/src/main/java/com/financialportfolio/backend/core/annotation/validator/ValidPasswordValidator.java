package com.financialportfolio.backend.core.annotation.validator;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.financialportfolio.backend.core.annotation.ValidPassword;
import com.financialportfolio.backend.core.annotation.validator.password.PasswordValidation;

@Component
public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {
    
    @Autowired
    private List<PasswordValidation> passwordValidations;
    
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

}
