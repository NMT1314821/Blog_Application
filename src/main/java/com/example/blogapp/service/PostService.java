package com.example.blogapp.service;

import com.example.blogapp.payload.PageResponse;
import com.example.blogapp.payload.PostDto;
import java.util.*;
public interface PostService 
{
	public PostDto createPost(PostDto postDto);
	
	public PageResponse getAllPosts(int pageNo,int pageSize,String sortBy,String sortDir);
	
	public PostDto getByPost(long id);
	
	public PostDto updatePost(PostDto postDto,long id);
	
	public void deletePost(long id);

	public List<PostDto> getPostsByCategory(long categoryId);

	

}
