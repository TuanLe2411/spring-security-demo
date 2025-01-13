package com.practice.security.configs;

import com.practice.security.exceptions.SecurityExceptionHandler;
import com.practice.security.filters.JwtFilter;
import com.practice.security.services.MyUserDetailService;
import com.practice.security.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    public final String[] PUBLIC_POST_REQUEST = new String[]{"/users", "/auth/login"};

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyUserDetailService userDetailService;

    @Autowired
    private SecurityExceptionHandler securityExceptionHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(
                request -> request
                    .requestMatchers(HttpMethod.POST, PUBLIC_POST_REQUEST)
                    .permitAll()
                    .anyRequest().authenticated()
            )
            .exceptionHandling(
                exception -> exception.authenticationEntryPoint(
                    this.securityExceptionHandler
                ))
            .addFilterAfter(new JwtFilter(jwtUtil, userDetailService), UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

}
