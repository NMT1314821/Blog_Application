package com.example.blogapp.config;

import org.springframework.context.annotation.Bean;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.blogapp.security.JwtAuthenticationEnteryPoint;
import com.example.blogapp.security.JwtAuthenticationFilter;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@EnableMethodSecurity
@SecurityScheme(
		name="Bearer Authentication",
		type=SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer"
		)

public class SecurityConfig 
{
	
	private UserDetailsService userDetailsService;
	
	private JwtAuthenticationFilter authenticationFilter;
	
	private JwtAuthenticationEnteryPoint authenticationEnteryPoint;
	

	
	public SecurityConfig(UserDetailsService userDetailsService,
			JwtAuthenticationEnteryPoint authenticationEnteryPoint,
			JwtAuthenticationFilter authenticationFilter) {
		super();
		this.userDetailsService = userDetailsService;
		this.authenticationEnteryPoint=authenticationEnteryPoint;
		this.authenticationFilter=authenticationFilter;
		
	}


	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception
	{
		return configuration.getAuthenticationManager();
	}
	
	
	@Bean
	public static PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain securityFilterChian(HttpSecurity http) throws Exception
	{
		
		http.csrf((csrf)->csrf.disable())
		.authorizeHttpRequests((auth)->auth.requestMatchers(HttpMethod.GET,"/api/v1/**").permitAll()
				.requestMatchers("/api/v1/auth/**").permitAll()
				.requestMatchers("/swagger-ui/**").permitAll()
				.requestMatchers("v3/api-docs/**").permitAll()
				.anyRequest().authenticated())
		
		.exceptionHandling(exception->
				exception.authenticationEntryPoint(authenticationEnteryPoint))
		.sessionManagement(session->session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.addFilterBefore(authenticationFilter,UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	// Whenever we use custom user Details then we no need to 
	//write in memory users.
	
//	@Bean
//	public UserDetailsService userDetailsService()
//	{
//		UserDetails naveen=User.builder()
//				.username("naveen")
//				.password(passwordEncoder().encode("naveen"))
//				.roles("USER")
//				.build();
//		
//		UserDetails admin=User.builder()
//				.username("admin")
//				.password(passwordEncoder().encode("admin"))
//				.roles("ADMIN")
//				.build();
//		return new InMemoryUserDetailsManager(naveen,admin);
//	}

}
