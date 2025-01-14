package com.practice.security.services;

import com.practice.security.constants.Error;
import com.practice.security.dtos.CreateUserDto;
import com.practice.security.enums.PermissionList;
import com.practice.security.enums.RoleList;
import com.practice.security.events.publishers.OnUserVerifyRequestPublisher;
import com.practice.security.exceptions.AppException;
import com.practice.security.mappers.UserMapper;
import com.practice.security.models.User;
import com.practice.security.models.UserVerifyRequest;
import com.practice.security.objects.UserResponse;
import com.practice.security.repositories.UserRepo;
import com.practice.security.repositories.UserVerifyRequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserVerifyRequestRepo userVerifyRequestRepo;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public UserResponse createUser(CreateUserDto createUserDto) throws AppException {
        User user = this.userMapper.toUser(createUserDto);
        Set<RoleList> roles = Set.of(RoleList.USER);
        Set<PermissionList> permissions = Set.of(PermissionList.READ, PermissionList.WRITE);
        user.setPassword(this.passwordEncoder.encode(createUserDto.getPassword()));
        user.setRoles(roles);
        user.setPermissions(permissions);
        user.setVerified(false);
        user.setLoggedIn(false);
        user.setLastLoginDate(null);
        try {
            this.userRepo.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(Error.USER_EXISTED);
        }
        UserVerifyRequest userVerifyRequest = this.createUserVerifyRequest(user.getId());
        this.applicationEventPublisher.publishEvent(new OnUserVerifyRequestPublisher(this, userVerifyRequest));
        return this.userMapper.toResponse(user);
    }

    @PreAuthorize("hasAuthority('USER') && hasAuthority('READ')")
    public UserResponse getUser(String username) throws AppException {
        User user = this.userRepo.findUserByUsername(username);
        if (user == null) {
            throw new AppException(Error.USER_NOT_FOUND);
        }
        return this.userMapper.toResponse(user);
    }

    private UserVerifyRequest createUserVerifyRequest(Long userId) {
        return this.userVerifyRequestRepo.save(new UserVerifyRequest(userId));
    }
}
