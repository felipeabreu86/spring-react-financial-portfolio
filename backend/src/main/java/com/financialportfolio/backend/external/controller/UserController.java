package com.financialportfolio.backend.external.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financialportfolio.backend.core.exception.UserNotFoundException;
import com.financialportfolio.backend.core.util.ConstantsUtil;
import com.financialportfolio.backend.domain.model.PasswordRecoveryToken;
import com.financialportfolio.backend.domain.model.User;
import com.financialportfolio.backend.domain.service.EmailService;
import com.financialportfolio.backend.domain.service.PasswordRecoveryService;
import com.financialportfolio.backend.domain.service.UserService;
import com.financialportfolio.backend.external.BaseController;
import com.financialportfolio.backend.external.dto.request.ChangePasswordDto;
import com.financialportfolio.backend.external.dto.request.RecoveryPasswordDto;
import com.financialportfolio.backend.external.dto.request.UserRegisterFormDto;
import com.financialportfolio.backend.external.dto.response.UserDataDto;

import io.vavr.control.Either;

@RestController
@RequestMapping("/user")
public class UserController implements BaseController {
    
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
                    return ApiSuccessResponse(HttpStatus.CREATED, new UserDataDto(user.getId()));
                });
    }
    
    @PostMapping(
            value = "/recovery-password",
            produces = MediaType.APPLICATION_JSON_VALUE, 
            consumes = MediaType.APPLICATION_JSON_VALUE,
            headers = { ConstantsUtil.CHANGE_PASSWORD_URI })
    public ResponseEntity<?> recoveryPassword(
            final HttpServletRequest request, 
            @Valid @RequestBody RecoveryPasswordDto forgotPasswordForm) {
        
        Either<UsernameNotFoundException, User> user = userService.findUserBy(forgotPasswordForm.getEmail());

        if (user.isLeft()) {
            return ApiErrorResponse(HttpStatus.BAD_REQUEST, user.getLeft());
        }

        Either<Exception, PasswordRecoveryToken> token = passwordRecoveryService.createPasswordRecoveryToken(user.get());

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
    
    @PutMapping(
            value = "/change-password", 
            produces = MediaType.APPLICATION_JSON_VALUE, 
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto) {

        Either<Exception, PasswordRecoveryToken> token = passwordRecoveryService
                .getPasswordRecoveryToken(changePasswordDto.getEmail(), changePasswordDto.getToken());

        if (token.isLeft()) {
            return ApiErrorResponse(HttpStatus.BAD_REQUEST, token.getLeft());
        }
        
        User user = token.get().getUser();
        user.setPassword(changePasswordDto.getPassword());

        return userService
                .saveOrUpdate(user)
                .fold(exception -> {
                    return ApiErrorResponse(HttpStatus.BAD_REQUEST, exception);
                }, count -> {
                    return ApiSuccessResponse(HttpStatus.OK, new UserDataDto(user.getId()));
                });
    }
    
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteUser(
            Principal principal, 
            @PathVariable(required = true, name = "id") Long userId) {

        if (!principal.getName().equals(userId.toString()) && !isAdmin()) {
            return ApiErrorResponse(HttpStatus.UNAUTHORIZED, "Não autorizado.");
        }

        Either<UserNotFoundException, User> user = userService.findUserBy(userId);

        return userService
                .delete(user.get())
                .fold(exception -> {
                    return ApiErrorResponse(HttpStatus.BAD_REQUEST, exception);
                }, count -> {
                    return ApiSuccessResponse(HttpStatus.OK, "Usuário removido com sucesso.");
                });
    }

}
