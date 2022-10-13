package com.financialportfolio.backend.external.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.financialportfolio.backend.core.annotation.FieldMatch;
import com.financialportfolio.backend.core.annotation.ValidPassword;
import com.financialportfolio.backend.core.util.ConstantsUtil;
import com.financialportfolio.backend.domain.model.User;

@FieldMatch(first = "password", second = "confirmPassword", message = "{password.does.not.match}")
public class UserRegisterFormDto {

    @JsonProperty("first_name")
    @NotBlank(message = "{user.first.name.not.blank}")
    @Size(max = 80, message = "{user.first.name.size}")
    private String firstName;

    @JsonProperty("last_name")
    @NotBlank(message = "{user.last.name.not.blank}")
    @Size(max = 80, message = "{user.last.name.size}")
    private String lastName;

    @Email(message = "{email.not.valid}", regexp = ConstantsUtil.EMAIL_REGEX_VALIDATION)
    @NotBlank(message = "{email.not.blank}")
    @Size(max = 256, message = "{email.max.size}")
    private String email;

    @NotBlank(message = "{password.not.blank}")
    @ValidPassword
    private String password;

    @NotBlank(message = "{password.confirmation.not.blank}")
    @JsonProperty("confirm_password")
    private String confirmPassword;

    public User toUser() {
        return new User(firstName, lastName, email, password);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}
