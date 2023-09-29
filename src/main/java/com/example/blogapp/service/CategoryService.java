package com.example.blogapp.service;

import com.example.blogapp.payload.CategoryDto;
import java.util.*;

public interface CategoryService
{
	
	CategoryDto createCategory(CategoryDto categoryDto);
	CategoryDto getCategory(long categoryId);
	List<CategoryDto> getAllCategories();
	CategoryDto updatateCategory(CategoryDto categoryDto,long categoryId);
	
	void deleteCategory(long id);
}
