package com.practice.security.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class UserVerifyRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private Long userId;

    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    public UserVerifyRequest(Long userId) {
        this.userId = userId;
    }

    public UserVerifyRequest() {

    }
}
