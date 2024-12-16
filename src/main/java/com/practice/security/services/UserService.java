package com.practice.security.services;

import com.practice.security.dtos.CreateUserDto;
import com.practice.security.enums.PermissionList;
import com.practice.security.enums.RoleList;
import com.practice.security.mappers.UserMapper;
import com.practice.security.models.User;
import com.practice.security.objects.UserResponse;
import com.practice.security.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepo userRepo;

    public UserResponse createUser(CreateUserDto createUserDto) {
        User user = this.userMapper.toUser(createUserDto);
        user.setPassword(this.passwordEncoder.encode(createUserDto.getPassword()));
        Set<RoleList> roles = Set.of(RoleList.USER);
        user.setRoles(roles);
        Set<PermissionList> permissions = Set.of(PermissionList.READ, PermissionList.WRITE);
        user.setPermissions(permissions);

        this.userRepo.save(user);
        return this.userMapper.toResponse(user);
    }
}
