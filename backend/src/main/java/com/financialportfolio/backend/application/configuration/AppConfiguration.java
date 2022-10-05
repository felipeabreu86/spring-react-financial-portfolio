package com.financialportfolio.backend.application.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.financialportfolio.backend.core.annotation.validator.password.PasswordValidation;
import com.financialportfolio.backend.core.annotation.validator.password.PasswordSpecialCharacterValidation;
import com.financialportfolio.backend.core.annotation.validator.password.PasswordUpperCaseValidation;
import com.financialportfolio.backend.core.annotation.validator.password.PasswordLowerCaseValidation;
import com.financialportfolio.backend.core.annotation.validator.password.PasswordLengthValidation;

@Configuration
public class AppConfiguration {

    @Bean
    public List<PasswordValidation> passwordValidations() {

        List<PasswordValidation> validations = new ArrayList<PasswordValidation>();
        validations.add(new PasswordLengthValidation());
        validations.add(new PasswordUpperCaseValidation());
        validations.add(new PasswordLowerCaseValidation());
        validations.add(new PasswordSpecialCharacterValidation());

        return validations;
    }

}
