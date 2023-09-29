package com.example.blogapp.payload;

import java.util.Set;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class PostDto 
{
	private Long id;
	@NotNull(message="title must not be empty")
	@Size(min=2,message="title at least 2 characters")
	private String title;
	
	@NotNull(message="description must not be empty")
	@Size(min=10,message="description at least 10 characters")
	private String description;
	
	private Long categoryId;
	
	@NotNull(message="content must not empty")
	private String content;
	
	private Set<CommentDto> comments;

}
