package com.practice.security.services;

import com.practice.security.constants.Error;
import com.practice.security.dtos.LoginDto;
import com.practice.security.exceptions.AppException;
import com.practice.security.models.User;
import com.practice.security.objects.LoginResponse;
import com.practice.security.repositories.UserRepo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.StringJoiner;

@Service
public class AuthService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Value("${jwt.valid-duration.ms}")
    protected long VALID_DURATION_MS;

    public AuthService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

//    @NonFinal
//    @Value("${jwt.refreshable-duration}")
//    protected long REFRESHABLE_DURATION;

    public LoginResponse login(LoginDto loginDto) throws AppException {
        Optional<User> user = this.userRepo.findUserByUsername(loginDto.getUsername());
        if(user.isEmpty()) {
            throw new AppException(Error.USER_NOT_FOUND);
        }
        if(this.isPasswordNotMatched(loginDto.getPassword(), user.get().getPassword())) {
            throw new AppException(Error.UNAUTHENTICATED);
        }
        String token = this.generateToken(user.get());
        return LoginResponse.builder().token(token).isSuccess(true).build();
    }

    private boolean isPasswordNotMatched(String password, String rawPassword) {
        return !this.passwordEncoder.matches(password, rawPassword);
    }

    private String generateToken(User user) {
        return Jwts.builder()
            .subject(user.getUsername())
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + this.VALID_DURATION_MS))
            .signWith(key)
            .claim("scope", buildScope(user))
            .compact();
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        if (!CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.name());
                if (!CollectionUtils.isEmpty(user.getPermissions()))
                    user.getPermissions().forEach(permission -> stringJoiner.add(permission.name()));
            });
        return stringJoiner.toString();
    }
}
