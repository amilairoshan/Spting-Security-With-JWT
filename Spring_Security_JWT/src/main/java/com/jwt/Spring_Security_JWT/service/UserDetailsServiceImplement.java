package com.jwt.Spring_Security_JWT.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.jwt.Spring_Security_JWT.Enum.UserRoles;
import com.jwt.Spring_Security_JWT.entity.Role;
import com.jwt.Spring_Security_JWT.entity.User;
import com.jwt.Spring_Security_JWT.repository.UserRepository;


@Component
public class UserDetailsServiceImplement implements UserDetailsService {
 
    @Autowired
    private UserRepository  userRepository;
     
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
    	Optional<User> user = userRepository.findByUsername(username);
    	
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found!");
        }
        
        return  UserDetailsServices.build(user.get());
    }
 
}
