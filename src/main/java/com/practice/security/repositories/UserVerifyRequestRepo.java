package com.practice.security.repositories;

import com.practice.security.models.UserVerifyRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserVerifyRequestRepo extends JpaRepository<UserVerifyRequest, Long> {
}
