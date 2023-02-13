package com.jwt.Spring_Security_JWT.service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jwt.Spring_Security_JWT.Enum.UserRoles;
import com.jwt.Spring_Security_JWT.dto.SignUpRequest;
import com.jwt.Spring_Security_JWT.entity.Role;
import com.jwt.Spring_Security_JWT.entity.User;
import com.jwt.Spring_Security_JWT.exception.InvalidCredentialException;
import com.jwt.Spring_Security_JWT.exception.UserAlreadyExistsException;
import com.jwt.Spring_Security_JWT.repository.RoleRepository;
import com.jwt.Spring_Security_JWT.repository.UserRepository;
import com.jwt.Spring_Security_JWT.utility.Validation;


@Service
public class UserService {

	@Autowired
	private  UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
    private  PasswordEncoder passwordEncoder;

    public User readUserByUsername (String username) {
        return userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
    }
    @Transactional
    public void createUser(SignUpRequest userLoginRequest) {
    	if(Objects.isNull(userLoginRequest) || Validation.checkIsNull(userLoginRequest.getUsername())|| Validation.checkIsNull(userLoginRequest.getPassword())  )
    	{
    		throw new InvalidCredentialException();
    	}
    	User user = new User();
        Optional<User> userName = userRepository.findByUsername(userLoginRequest.getUsername());
        
        if (userName.isPresent()) {
            throw new UserAlreadyExistsException();
        }
        user.setUsername(userLoginRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userLoginRequest.getPassword()));
        Set<String> userRoles = userLoginRequest.getRole();
        
        Set<Role> roles = new HashSet<>();

		if (userRoles == null) {
			Role userRole = roleRepository.findByName(UserRoles.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			userRoles.forEach(role -> {
				switch (role) {
				case "user":
					Role usersRole = roleRepository.findByName(UserRoles.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(usersRole);

					break;
				case "admin":
					Role adminRole = roleRepository.findByName(UserRoles.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				
				}
			});
		}

		user.setRoles(roles);
        userRepository.save(user);
    }
	
	
	
	
	
	
	
	
}
