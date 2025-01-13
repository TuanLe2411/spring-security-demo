package com.practice.security.services;

import com.practice.security.constants.Error;
import com.practice.security.dtos.LoginDto;
import com.practice.security.exceptions.AppException;
import com.practice.security.models.User;
import com.practice.security.objects.LoginResponse;
import com.practice.security.repositories.UserRepo;
import com.practice.security.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse login(LoginDto loginDto) throws AppException {
        User user = this.userRepo.findUserByUsername(loginDto.getUsername());
        if (user == null) {
            throw new AppException(Error.USER_NOT_FOUND);
        }
        if (this.isPasswordNotMatched(loginDto.getPassword(), user.getPassword())) {
            throw new AppException(Error.UNAUTHENTICATED);
        }
        String token = this.jwtUtil.generateToken(user);
        user.setLastLoginDate(new Date());
        user.setLoggedIn(true);
        this.userRepo.save(user);
        return LoginResponse.builder().token(token).isSuccess(true).build();
    }

    private boolean isPasswordNotMatched(String password, String rawPassword) {
        return !this.passwordEncoder.matches(password, rawPassword);
    }


}
