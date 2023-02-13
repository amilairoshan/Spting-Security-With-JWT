package com.jwt.Spring_Security_JWT.exception;

public class UserAlreadyExistsException extends RuntimeException {

	public UserAlreadyExistsException() {
		super("User already registered. Please use different username.");
	}
}
