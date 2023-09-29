package com.example.blogapp.controller;

import java.util.ArrayList;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogapp.payload.PageResponse;
import com.example.blogapp.payload.PostDto;
import com.example.blogapp.service.PostService;
import com.example.blogapp.util.AppConstants;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping()
@AllArgsConstructor
public class PostController 
{
	@Autowired
	private PostService postServ;
	
	@SecurityRequirement(
			name="Bearer Authentication"
			)
	@PostMapping("/api/v1/posts")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody  PostDto postDto)
	{
		return new ResponseEntity<>(postServ.createPost(postDto),HttpStatus.CREATED);	
	}
	
	@GetMapping("/api/v1/posts")
	public ResponseEntity<PageResponse> getAllPosts(
			@RequestParam(value="pageNo",defaultValue =AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
			@RequestParam(value="pageSize",defaultValue =AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
			@RequestParam(value="sortBy",defaultValue =AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
			@RequestParam(value="sortDir",defaultValue =AppConstants.DEFAULT_SORT_DIR,required =false) String sortDir
			
			)
	{
		return new ResponseEntity<>(postServ.getAllPosts(pageNo,pageSize,sortBy,sortDir),HttpStatus.OK);
	}
	
	//get post by id rest api
	
	@GetMapping("/api/v1/posts/{id}")
	public ResponseEntity<PostDto> getByPostV1(@PathVariable("id") int pid)
	{
		return ResponseEntity.ok(postServ.getByPost(pid));
	}

	@SecurityRequirement(
			name="Bearer Authentication"
			)
	@PutMapping("/api/v1/posts/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,@PathVariable("id") long pid)
	{
		return new ResponseEntity<>(postServ.updatePost(postDto, pid),HttpStatus.OK);
	}
	
	@SecurityRequirement(
			name="Bearer Authentication"
			)
	@DeleteMapping("/api/v1/posts/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deletePost(@PathVariable("id") long pid)
	{
		postServ.deletePost(pid);
		return new ResponseEntity<>("successfully deleted",HttpStatus.OK);
	}
	
	
	//get all posts by category by id;
	@GetMapping("/api/posts/category/{id}")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("id") long categoryId)
	{
		List<PostDto> gpbcid=postServ.getPostsByCategory(categoryId); 
		return new ResponseEntity<>(gpbcid,HttpStatus.OK);
	}
	
	
}
