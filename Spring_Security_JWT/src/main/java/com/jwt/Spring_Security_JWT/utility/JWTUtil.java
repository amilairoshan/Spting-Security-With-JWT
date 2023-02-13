package com.jwt.Spring_Security_JWT.utility;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jwt.Spring_Security_JWT.constant.AuthenticationConfigConstants;

@Component
public class JWTUtil {
	
	private String secret=AuthenticationConfigConstants.SECRET;
	
	public String generateToken(String email) throws IllegalArgumentException, JWTCreationException {
        try {
        	return JWT.create()
                    .withSubject(AuthenticationConfigConstants.SUBJECT)
                    .withClaim("email", email)
                    .withIssuedAt(new Date())
                    .withIssuer(AuthenticationConfigConstants.ISSUER_NAME)
                    .sign(Algorithm.HMAC256(secret));
		} catch (IllegalArgumentException e) {
			return e.getMessage();
		}
        catch (JWTCreationException e) {
        	return e.getMessage();
		}
    }

    // Method to verify the JWT and then decode and extract the user email stored in the payload of the token
    public String validateTokenAndRetrieveSubject(String token)throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject(AuthenticationConfigConstants.SUBJECT)
                .withIssuer(AuthenticationConfigConstants.ISSUER_NAME)
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("email").asString();
    }
}
