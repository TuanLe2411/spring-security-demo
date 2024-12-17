package com.practice.security.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginDto {
    private String username;
    private String password;
}