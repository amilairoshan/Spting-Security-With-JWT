package com.jwt.Spring_Security_JWT.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jwt.Spring_Security_JWT.repository.UserRepository;
import com.jwt.Spring_Security_JWT.service.UserDetailsServiceImplement;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
	    securedEnabled = true,
	    jsr250Enabled = true,
	    prePostEnabled = true
	)
public class JWTSecurityConfiguration  extends WebSecurityConfigurerAdapter {

	@Autowired 
	private UserRepository userRepository;
    @Autowired 
    private JWTFilterConfig filterConfig;
    @Autowired 
    private UserDetailsServiceImplement detailsServiceImplement;
	
	
    @Override
    protected void configure(HttpSecurity http) throws Exception { // Method to configure your app security
        http.csrf().disable() // Disabling csrf
                .httpBasic().disable() // Disabling http basic
                .cors() // Enabling cors
                .and()
                .authorizeHttpRequests() // Authorizing incoming requests
                .antMatchers("/auth/**").permitAll() // Allows auth requests to be made without authentication of any sort
               // .antMatchers("/user/**").hasRole("USER") // Allows only users with the "USER" role to make requests to the user routes
               // .and()
                .anyRequest().authenticated().and()
                .userDetailsService(detailsServiceImplement) // Setting the user details service to the custom implementation
                .exceptionHandling()
                    .authenticationEntryPoint(
                            // Rejecting request as unauthorized when entry point is reached
                            // If this point is reached it means that the current request requires authentication
                            // and no JWT token was found attached to the Authorization header of the current request.
                            (request, response, authException) ->
                                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
                    )
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Setting Session to be stateless

        // Adding the JWT filter
        http.addFilterBefore(filterConfig, UsernamePasswordAuthenticationFilter.class);
    }

    // Creating a bean for the BCryptPasswordEncoder password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Create the bean of the authentication manager which will be used to run the authentication process
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	
	
	
}
