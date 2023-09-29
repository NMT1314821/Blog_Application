package com.example.blogapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogapp.payload.LoginDto;
import com.example.blogapp.payload.RegisterDto;
import com.example.blogapp.security.JwtAuthResponse;
import com.example.blogapp.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController
{
	@Autowired
	private AuthService authService;
	
	
	public AuthController(AuthService authService)
	{
		this.authService=authService;
	}
	
	@PostMapping(value={"/login","/signin"})
	public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto)
	{
		String token=authService.login(loginDto);
		JwtAuthResponse ar=new JwtAuthResponse();
		ar.setAccessToken(token);
		return ResponseEntity.ok(ar);
	}
	
	@PostMapping(value= {"/register","/singup"})
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto)
	{
		String response=authService.register(registerDto);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}

}
