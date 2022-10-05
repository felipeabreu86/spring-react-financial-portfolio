package com.financialportfolio.backend.external.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.financialportfolio.backend.core.annotation.FieldMatch;
import com.financialportfolio.backend.core.annotation.ValidPassword;
import com.financialportfolio.backend.domain.model.User;

@FieldMatch(first = "password", second = "confirmPassword", message = "{password.does.not.match}")
public class UserRegisterFormDto {

    @JsonProperty("first_name")
    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 80, message = "O nome deve conter até 80 dígitos.")
    private String firstName;

    @JsonProperty("last_name")
    @NotBlank(message = "O sobrenome é obrigatório.")
    @Size(max = 80, message = "O sobrenome deve conter até 80 dígitos.")
    private String lastName;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Size(max = 50, message = "O e-mail deve conter até 50 dígitos.")
    private String email;

    @NotBlank(message = "{password.not.blank}")
    @ValidPassword
    private String password;

    @NotBlank(message = "{confirm.password.not.blank}")
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
