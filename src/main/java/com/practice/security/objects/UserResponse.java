package com.practice.security.objects;

import com.practice.security.enums.PermissionList;
import com.practice.security.enums.RoleList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private Date lastLoginDate;
    private boolean loggedIn;
    private Set<RoleList> roles;
    private Set<PermissionList> permissions;
}
