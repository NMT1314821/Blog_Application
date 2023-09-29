package com.example.blogapp.security;

import java.io.IOException;


import org.springframework.util.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService; 
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter
{

	private JwtTokenProvider jwtTokenProvider;
	
	private UserDetailsService userDetailsService;
	
	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
		super();
		this.jwtTokenProvider=jwtTokenProvider;
		this.userDetailsService = userDetailsService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response, 
									FilterChain filterChain)throws ServletException, IOException {
	
		//get JWT Token from HTTP request
		
		String token=getTokenFromRequest(request);
		
		//Validate Teken
		if(StringUtils.hasText(token)&& jwtTokenProvider.validateToken(token))
		{
			//get username from token
			String  username=jwtTokenProvider.getUsername(token);
			
			//Load the user associated with Token
			
			UserDetails userDetails=userDetailsService.loadUserByUsername(username);
			
			UsernamePasswordAuthenticationToken authentication=new 
					UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
			
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
		}
		
		filterChain.doFilter(request, response);
					
	}
	
	
	private String getTokenFromRequest(HttpServletRequest request)
	{
		String bearerToken=request.getHeader("Authorization");
		if(StringUtils.hasText(bearerToken)&& bearerToken.startsWith("Bearer"))
		{
			return bearerToken.substring(7,bearerToken.length());
		}
		return null;
	}
	

}

