package com.practice.security.utils;

import com.practice.security.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.StringJoiner;

@Component
@Scope("singleton")
public class JwtUtil {
    private static final SecretKey key = Jwts.SIG.HS256.key().build();

    @Value("${jwt.valid-duration.ms}")
    protected long VALID_DURATION_MS;

//    @Value("${jwt.refreshable-duration}")
//    protected long REFRESHABLE_DURATION;

    public String generateToken(User user) {
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
                stringJoiner.add(role.name());
                if (!CollectionUtils.isEmpty(user.getPermissions()))
                    user.getPermissions().forEach(permission -> stringJoiner.add(permission.name()));
            });
        return stringJoiner.toString();
    }

    @Nullable
    public Claims extractJwt(String token) {
        JwtParser jwtParser = Jwts.parser()
            .verifyWith(key)
            .build();
        try {
            return (Claims) jwtParser.parse(token).getPayload();
        } catch (Exception e) {
            System.out.println("Could not verify JWT token integrity!, err: " + e);
            return null;
        }
    }

    @Nullable
    public String extractUsername(String token) {
        Claims claims = this.extractJwt(token);
        if (claims == null) {
            return null;
        }
        return claims.getSubject();
    }
}
