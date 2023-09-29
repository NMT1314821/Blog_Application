package com.example.blogapp.controller;

import org.apache.catalina.connector.Response;
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
import org.springframework.web.bind.annotation.RestController;

import com.example.blogapp.payload.CategoryDto;
import com.example.blogapp.service.CategoryService;

import lombok.AllArgsConstructor;
import java.util.*;

@RestController
@RequestMapping("/api/v1/categories")
@AllArgsConstructor
public class CategoryController 
{
	@Autowired
	private CategoryService categoryServ;
	
	@PostMapping()
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto)
	{
		CategoryDto nc=categoryServ.createCategory(categoryDto);
		return new ResponseEntity<>(nc,HttpStatus.CREATED);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") long categoryId)
	{
		CategoryDto nc=categoryServ.getCategory(categoryId);
		return new ResponseEntity<>(nc,HttpStatus.OK);
		
	}
	
	@GetMapping
	public ResponseEntity<List<CategoryDto>> getAllCategories()
	{
		return ResponseEntity.ok(categoryServ.getAllCategories());
	}
	
	
	@PutMapping("{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto> updateCategory(@PathVariable("id")long categoryId,
					@RequestBody CategoryDto categoryDto)
	{
		CategoryDto uc=categoryServ.updatateCategory(categoryDto, categoryId);
		return new ResponseEntity<>(uc,HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteCategory(@PathVariable("id") long categoryId)
	{
		categoryServ.deleteCategory(categoryId);
		return new ResponseEntity<>("successfully deleted",HttpStatus.OK);
	}
	
	
	

}
