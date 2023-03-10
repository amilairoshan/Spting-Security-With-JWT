package com.jwt.Spring_Security_JWT.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jwt.Spring_Security_JWT.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
   public Optional<User>  findByUsername(String username);
    
}
