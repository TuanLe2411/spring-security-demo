package com.practice.security.controllers;

import com.practice.security.dtos.CreateUserDto;
import com.practice.security.objects.ApiResponse;
import com.practice.security.objects.UserResponse;
import com.practice.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping()
    public ApiResponse<UserResponse> createUser(@RequestBody CreateUserDto createUserDto) {
        UserResponse userResponse = this.userService.createUser(createUserDto);

        return ApiResponse.<UserResponse>builder()
            .result(userResponse)
            .build();
    }
}
