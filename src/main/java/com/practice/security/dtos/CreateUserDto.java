package com.practice.security.dtos;

import lombok.Data;

import java.util.Date;


@Data
public class CreateUserDto {
    private String username;
    private String password;
    private Date dateOfBirth;
}
