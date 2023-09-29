package com.example.blogapp.security;

import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.example.blogapp.exception.BlogAppApiException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider 
{
	@Value("${app.jwt-secret}")
	private String jwtSecret;
	@Value("${app-jwt-expiration-milliseconds}")
	private long JwtExpirationDate;

	//Generate JWT TOKEN
	
	public String generateToken(Authentication authentication)
	{
		String username=authentication.getName();
		
		Date currentdate=new Date();
		
		Date expiryDate=new Date(currentdate.getTime()+JwtExpirationDate);
		
		String token=Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(expiryDate)
				.signWith(key())
				.compact();
		return token;
	}
	private Key key()
	{
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));		
	}
	//Get UserName from JwtToken
	
	public String getUsername(String token)
	{
		Claims claims=Jwts.parserBuilder()
		.setSigningKey(key())
		.build()
		.parseClaimsJws(token)
		.getBody();
		String username=claims.getSubject();
		return username;
		
	}
	
	//Validate JWT Token
	
	public boolean validateToken(String token)
	{
		try {
		Jwts.parserBuilder()
		.setSigningKey(key())
		.build()
		.parse(token);
		
		return true;
	}
	catch (MalformedJwtException e)
	{
		throw new BlogAppApiException(HttpStatus.BAD_REQUEST,"invalid JWT TOken");
	}
	catch (ExpiredJwtException e) 
	{
	
		throw new BlogAppApiException(HttpStatus.BAD_REQUEST,"expired JWT TOken");
	}
	catch(UnsupportedJwtException e)
	{
		throw new BlogAppApiException(HttpStatus.BAD_REQUEST,"unsuppored JWT Toen");
	}
	catch(IllegalArgumentException e) 
	{
		throw new BlogAppApiException(HttpStatus.BAD_REQUEST,"Invalid cliam String is empty");
	}
	}	
}
