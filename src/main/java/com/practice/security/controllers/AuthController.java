package com.practice.security.controllers;

import com.practice.security.dtos.LoginDto;
import com.practice.security.exceptions.AppException;
import com.practice.security.objects.ApiResponse;
import com.practice.security.objects.LoginResponse;
import com.practice.security.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginDto loginDto) throws AppException {
        LoginResponse loginResponse = this.authService.login(loginDto);
        return ApiResponse.<LoginResponse>builder()
            .result(loginResponse)
            .build();
    }
}
