package com.example.blogapp.service;

import com.example.blogapp.payload.LoginDto;
import com.example.blogapp.payload.RegisterDto;

public interface AuthService 
{
	public String login(LoginDto loginDto);
	public String register(RegisterDto registerDto);
	

}
