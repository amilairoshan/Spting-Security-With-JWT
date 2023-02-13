package com.jwt.Spring_Security_JWT.controller;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.Spring_Security_JWT.Enum.UserRoles;
import com.jwt.Spring_Security_JWT.entity.User;
import com.jwt.Spring_Security_JWT.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	 @RolesAllowed({UserRoles.RoleNames.USER})
	 @GetMapping("/userinfo")
	    public String getUserDetails(){
	        // Retrieve email from the Security Context
	        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        // Fetch and return user details
	        return  String.format("The logged user name is %s",email);
	       
	    }

}
