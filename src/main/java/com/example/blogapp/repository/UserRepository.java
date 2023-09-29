package com.example.blogapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blogapp.model.User;

public interface UserRepository extends JpaRepository<User,Long> 
{
	Optional<User> findByUsernameOrEmail(String email,String username);
	Optional<User> findByEmail(String email);
	Optional<User> findByUsername(String username);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
	
}
