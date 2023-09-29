package com.example.blogapp.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto 
{
	private long id;
	
	@NotNull(message="name must not be empty")
	private String name;
	
	@NotNull(message="email must not be empty")
	@Email
	private String email;
	
	@NotNull(message="body must not be empty")
	@Size(min=10,message="body at least 10 characters")
	private String body;

}
