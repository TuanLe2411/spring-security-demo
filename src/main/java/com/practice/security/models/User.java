package com.practice.security.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.practice.security.enums.PermissionList;
import com.practice.security.enums.RoleList;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String password;
    @Column(name = "username", unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    private String username;
    private Date dateOfBirth;
    private Date lastLoginDate;
    private boolean loggedIn;
    private boolean isVerified;

    private Set<RoleList> roles;

    private Set<PermissionList> permissions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roleAuths = roles.stream().map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());
        List<GrantedAuthority> permissionAuths = permissions.stream().map(permission -> new SimpleGrantedAuthority(permission.name())).collect(Collectors.toList());
        roleAuths.addAll(permissionAuths);
        return roleAuths;
    }
}
