package com.jwt.Spring_Security_JWT.controller;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.Spring_Security_JWT.dto.SignInRequest;
import com.jwt.Spring_Security_JWT.dto.SignUpRequest;
import com.jwt.Spring_Security_JWT.entity.User;
import com.jwt.Spring_Security_JWT.repository.UserRepository;
import com.jwt.Spring_Security_JWT.service.UserDetailsServiceImplement;
import com.jwt.Spring_Security_JWT.service.UserDetailsServices;
import com.jwt.Spring_Security_JWT.service.UserService;
import com.jwt.Spring_Security_JWT.utility.JWTUtil;

@RestController 
@RequestMapping("/auth") 
public class AuthenticationController {

    @Autowired 
    private JWTUtil jwtUtil;
    
    @Autowired
    private AuthenticationManager authManager;
    
    @Autowired
    private UserService userService;
   
    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signUpHandler(@RequestBody SignUpRequest loginDetails){
     
        // Save User Entity on Database
    	userService.createUser(loginDetails);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.CREATED);
        body.put("message",  "User registered successfully!");
        return new ResponseEntity<>(body, HttpStatus.CREATED); 
    }

  
    @PostMapping("/signin")
    public ResponseEntity<Map<String, Object>> signinHandler(@RequestBody SignUpRequest signInRequest){
     
    	 Authentication authentication = authManager.authenticate(
 				new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));

 		SecurityContextHolder.getContext().setAuthentication(authentication);
 		
 		UserDetailsServices userDetails = (UserDetailsServices) authentication.getPrincipal();		
 		List<String> roles = userDetails.getAuthorities().stream()
 				.map(item -> item.getAuthority())
 				.collect(Collectors.toList());
        
            String token = jwtUtil.generateToken(signInRequest.getUsername());
            Map<String, Object> payload = new LinkedHashMap<>();
    		payload.put("timestamp", LocalDateTime.now());
    		payload.put("user_name",  userDetails.getUsername());
    		payload.put("roles",  roles);
    		payload.put("jwt-token",  token);
   
            return new ResponseEntity<>(payload, HttpStatus.CREATED); 
  
    }
	
}
