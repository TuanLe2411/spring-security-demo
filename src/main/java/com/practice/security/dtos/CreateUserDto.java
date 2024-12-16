package com.practice.security.dtos;

import lombok.Data;

import java.sql.Date;

@Data
public class CreateUserDto {
    private String username;
    private String password;
    private Date dateOfBirth;
}
