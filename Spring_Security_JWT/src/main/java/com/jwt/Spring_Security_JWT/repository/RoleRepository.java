package com.jwt.Spring_Security_JWT.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jwt.Spring_Security_JWT.Enum.UserRoles;
import com.jwt.Spring_Security_JWT.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(UserRoles name);
}
