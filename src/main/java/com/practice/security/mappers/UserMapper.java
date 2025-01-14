package com.practice.security.mappers;

import com.practice.security.dtos.CreateUserDto;
import com.practice.security.models.User;
import com.practice.security.objects.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toUser(CreateUserDto createUserDto) {
        return User.builder()
            .username(createUserDto.getUsername())
            .dateOfBirth(createUserDto.getDateOfBirth())
            .loggedIn(false)
            .build();
    }

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
            .id(user.getId())
            .username(user.getUsername())
            .loggedIn(user.isLoggedIn())
            .lastLoginDate(user.getLastLoginDate())
            .isVerified(user.isVerified())
            .build();
    }
}
