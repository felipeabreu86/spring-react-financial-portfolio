package com.financialportfolio.backend.external.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financialportfolio.backend.domain.service.UserService;
import com.financialportfolio.backend.external.ApiResponse;
import com.financialportfolio.backend.external.dto.request.UserRegisterFormDto;
import com.financialportfolio.backend.external.dto.response.UserCreatedDto;

@RestController
@RequestMapping("/user")
public class UserController implements ApiResponse {
    
    @Autowired
    private UserService userService;
    
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

}
