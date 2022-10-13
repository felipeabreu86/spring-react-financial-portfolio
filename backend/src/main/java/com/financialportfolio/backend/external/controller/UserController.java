package com.financialportfolio.backend.external.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financialportfolio.backend.domain.model.PasswordRecoveryToken;
import com.financialportfolio.backend.domain.model.User;
import com.financialportfolio.backend.domain.service.EmailService;
import com.financialportfolio.backend.domain.service.PasswordRecoveryService;
import com.financialportfolio.backend.domain.service.UserService;
import com.financialportfolio.backend.external.ApiResponse;
import com.financialportfolio.backend.external.dto.request.ForgotPasswordDto;
import com.financialportfolio.backend.external.dto.request.UserRegisterFormDto;
import com.financialportfolio.backend.external.dto.response.UserCreatedDto;

import io.vavr.control.Either;

@RestController
@RequestMapping("/user")
public class UserController implements ApiResponse {
    
    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordRecoveryService passwordRecoveryService;
    
    @PostMapping(
            value = "/register",
            produces = MediaType.APPLICATION_JSON_VALUE, 
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegisterFormDto userRegisterForm) {

        return userService
                .createNew(userRegisterForm.toUser())
                .fold(exception -> {
                    return ApiErrorResponse(HttpStatus.BAD_REQUEST, exception);
                }, user -> {
                    return ApiSuccessResponse(HttpStatus.CREATED, new UserCreatedDto(user.getId()));
                });
    }
    
    @PostMapping(
            value = "/recovery-password",
            produces = MediaType.APPLICATION_JSON_VALUE, 
            consumes = MediaType.APPLICATION_JSON_VALUE,
            headers = { "Change-Password-Uri" })
    public ResponseEntity<?> recoveryPassword(final HttpServletRequest request, @Valid @RequestBody ForgotPasswordDto forgotPasswordForm) {
        
        Either<UsernameNotFoundException, User> user = userService.findUserBy(forgotPasswordForm.getEmail());

        if (user.isLeft()) {
            return ApiErrorResponse(HttpStatus.BAD_REQUEST, user.getLeft());
        }

        Either<Exception, PasswordRecoveryToken> token = passwordRecoveryService
                .createPasswordRecoveryToken(user.get());

        if (token.isLeft()) {
            return ApiErrorResponse(HttpStatus.BAD_REQUEST, token.getLeft());
        }
        
        return emailService
                .sendPasswordRecoveryTokenEmail(request, token.get().getToken(), user.get())
                .fold(exception -> {
                    return ApiErrorResponse(HttpStatus.BAD_REQUEST, exception);
                }, count -> {
                    return ResponseEntity.ok("E-mail de recuperação de senha enviado.");
                });
    }

}
