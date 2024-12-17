package com.practice.security.objects;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponse {
    private String token;
    private boolean isSuccess;
}
