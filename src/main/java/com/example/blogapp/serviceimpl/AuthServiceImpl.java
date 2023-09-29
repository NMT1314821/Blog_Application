package com.example.blogapp.serviceimpl;

import java.util.HashSet;
import java.util.Set;

import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.blogapp.BlogAppApplication;
import com.example.blogapp.exception.BlogAppApiException;
import com.example.blogapp.model.Role;
import com.example.blogapp.model.User;
import com.example.blogapp.payload.LoginDto;
import com.example.blogapp.repository.RoleRepository;
import com.example.blogapp.repository.UserRepository;
import com.example.blogapp.security.JwtTokenProvider;
import com.example.blogapp.service.AuthService;
import com.example.blogapp.payload.RegisterDto;
@Service
class AuthServiceImpl implements AuthService
{
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private RoleRepository roleRepo;

	private JwtTokenProvider jwtTokenProvider;
	
	public AuthServiceImpl(AuthenticationManager authenticationManager,
							UserRepository userRepo,
							RoleRepository roleRepo,
							PasswordEncoder passwordEncoder,
							JwtTokenProvider jwtTokenProvider)
	{
		this.authenticationManager=authenticationManager;
		this.userRepo=userRepo;
		this.roleRepo=roleRepo;
		this.passwordEncoder=passwordEncoder;
		this.jwtTokenProvider=jwtTokenProvider;
	}
	
	@Override
	public String login(LoginDto loginDto) 
	{
		
		Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),
				loginDto.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token=jwtTokenProvider.generateToken(authentication);
		return token;
	}

	@Override
	public String register(RegisterDto registerDto) 
	{
		//chechk existuser or not
		if(userRepo.existsByUsername(registerDto.getUsername()))
		{
			throw new BlogAppApiException(HttpStatus.BAD_REQUEST,"Exists username");
		}
		if(userRepo.existsByEmail(registerDto.getEmail()))
		{
			throw new BlogAppApiException(HttpStatus.BAD_REQUEST,"Email alreaady Exists");
		}
		User user=new User();
		user.setName(registerDto.getName());
		user.setEmail(registerDto.getEmail());
		user.setUsername(registerDto.getUsername());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		
		Set<Role> roles=new HashSet<>();
		Role userrole=roleRepo.findByName("ROLE_USER").get();
		roles.add(userrole);
		user.setRoles(roles);
		userRepo.save(user);
		
		return "user successfully registered";
	}
	
}
