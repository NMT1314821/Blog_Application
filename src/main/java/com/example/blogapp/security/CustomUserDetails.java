package com.example.blogapp.security;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.blogapp.model.User;
import com.example.blogapp.repository.UserRepository;
@Service
public class CustomUserDetails implements UserDetailsService
{
	// write custom user details process
	//1)create User and roles with manytoone mapping unidirectional
	//2)then write userRepository and write some customQueryMethods
	//3)create security pkg and create customuserdetail class implements uds interface
	//4)write userRepository and rise exception the roles to gratauthorites
	//5)return user getemail,getpasswored,authorities.
	
	
	
	@Autowired
	private UserRepository userRepo;


	public CustomUserDetails(UserRepository userRepo) {
		super();
		this.userRepo = userRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException 
	{
		User user= userRepo.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
				.orElseThrow(()->new UsernameNotFoundException("user not found with username or email"+usernameOrEmail));
		Set<GrantedAuthority> authorities=user
				.getRoles()
				.stream()
				.map((role)-> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toSet());
		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
	
	}

}
