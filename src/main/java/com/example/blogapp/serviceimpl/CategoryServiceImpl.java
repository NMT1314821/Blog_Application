package com.example.blogapp.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.example.blogapp.exception.ResourceNotFoundException;
import com.example.blogapp.model.Category;
import com.example.blogapp.payload.CategoryDto;
import com.example.blogapp.repository.CategoryRepository;
import com.example.blogapp.service.CategoryService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService
{
	@Autowired
	private CategoryRepository categoryRepo;
	
	private ModelMapper mapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto)
	{
		//dto to jpa
		
		Category category=mapper.map(categoryDto,Category.class);
		Category newcategory=categoryRepo.save(category);
		return mapper.map(newcategory,CategoryDto.class);
	}

	@Override
	public CategoryDto getCategory(long categoryId) 
	{
		Category category=categoryRepo.findById(categoryId).orElseThrow(()->
		new ResourceNotFoundException("Category","id", categoryId));
		return mapper.map(category,CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		
		List<Category> allcategories=categoryRepo.findAll();
		return allcategories.stream().map((s)->mapper.map(s,CategoryDto.class)).collect(Collectors.toList());
	}

	@Override
	public CategoryDto updatateCategory(CategoryDto categoryDto,long categoryId) {
		Category category=categoryRepo.findById(categoryId).orElseThrow(()->
		new ResourceNotFoundException("Category","id", categoryId));
		
		category.setName(categoryDto.getName());
		category.setDescription(categoryDto.getDescription());
		return mapper.map(category,CategoryDto.class);
		
	}

	@Override
	public void deleteCategory(long categoryId)
	{
		Category category=categoryRepo.findById(categoryId).orElseThrow(()->
		new ResourceNotFoundException("Category","id", categoryId));
		categoryRepo.deleteById(categoryId);
	}
	
	
	


}
