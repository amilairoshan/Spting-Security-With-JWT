package com.jwt.Spring_Security_JWT.utility;

public class Validation {

	public static boolean checkIsNull(String word) {
		if (word == null || word.isEmpty() || word.trim().isEmpty()) 
		    return true;
		else
		    return false;
	}
	
	
	
	
}
