package com.practice.security.controllers;

import com.practice.security.dtos.CreateUserDto;
import com.practice.security.exceptions.AppException;
import com.practice.security.objects.ApiResponse;
import com.practice.security.objects.UserResponse;
import com.practice.security.services.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping()
    public ApiResponse<UserResponse> createUser(@RequestBody CreateUserDto createUserDto) throws AppException {
        UserResponse userResponse = this.userService.createUser(createUserDto);
        return ApiResponse.<UserResponse>builder()
            .result(userResponse)
            .build();
    }

    @GetMapping()
    public ApiResponse<UserResponse> getUser(@RequestParam(value = "username") String username) throws AppException {
        UserResponse userResponse = this.userService.getUser(username);
        return ApiResponse.<UserResponse>builder()
            .result(userResponse)
            .build();
    }
}
