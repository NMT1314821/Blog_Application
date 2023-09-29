package com.example.blogapp.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncryptBy
{
	public static void main(String[] args) 
	{
		PasswordEncoder pe=new BCryptPasswordEncoder();
		System.out.println(pe.encode("naveen"));
		System.out.println(pe.encode("admin"));
		
	}

}
