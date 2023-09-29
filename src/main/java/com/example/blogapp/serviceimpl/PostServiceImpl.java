package com.example.blogapp.serviceimpl;
import java.util.List;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.blogapp.exception.ResourceNotFoundException;
import com.example.blogapp.model.Category;
import com.example.blogapp.model.Post;
import com.example.blogapp.payload.PageResponse;
import com.example.blogapp.payload.PostDto;
import com.example.blogapp.repository.CategoryRepository;
import com.example.blogapp.repository.PostRepository;
import com.example.blogapp.service.PostService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService 
{
	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private CategoryRepository categoryRepo;
	

	@Override
	public PostDto createPost(PostDto postDto) 
	{
		Category category=categoryRepo.findById(postDto.getCategoryId()).orElseThrow(()-> new ResourceNotFoundException("Category","id",postDto.getCategoryId()));
		
		//dto to post
		Post post=mapToPost(postDto);
		post.setCategory(category);
		Post p= postRepo.save(post);
		
		//post to dto
		//PostDto newpost=mapToDto(p);
		return mapToDto(p) ;
				
	}
	
	private PostDto mapToDto(Post post)
	{
		
		PostDto postDto=mapper.map(post,PostDto.class);
		return postDto;
		
//		PostDto postDto=new PostDto();
//		postDto.setId(post.getId());
//		postDto.setTitle(post.getTitle());
//		postDto.setDescription(post.getDescription());
//		postDto.setContent(post.getContent());
//		
//		return postDto;		
	}
	private Post mapToPost(PostDto postDto)
	{
		Post post=mapper.map(postDto,Post.class);
		return post;
		
//		Post p=new Post();
//		p.setTitle(postDto.getTitle());
//		p.setDescription(postDto.getDescription());
//		p.setContent(postDto.getContent());
		
		//return p;
	}

	@Override
	public PageResponse getAllPosts(int pageNo,int pageSize,String sortBy,String sortDir) 
	{
		Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?
				Sort.by(sortBy).ascending():
				Sort.by(sortBy).descending();
		//create pageble instance
		Pageable pageable= PageRequest.of(pageNo, pageSize,sort);
		
		Page<Post> posts=postRepo.findAll(pageable);
		
		//getContent for page object
		List<Post> postsContent=posts.getContent();
		List<PostDto> content= postsContent.stream().map((p)->mapToDto(p)).collect(Collectors.toList());
		
		PageResponse pageRes=new PageResponse();
		pageRes.setContent(content);
		pageRes.setPageNo(posts.getNumber());
		pageRes.setPageSize(posts.getSize());
		pageRes.setTotalElements(posts.getTotalElements());
		pageRes.setTotalPages(posts.getTotalPages());
		pageRes.setLast(posts.isLast());
		return pageRes;
		
	}

	@Override
	public PostDto getByPost(long id) {
		Post post=postRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("post","id",id));
		return mapToDto(post) ;
	}

	@Override
	public PostDto updatePost(PostDto postDto, long id) 
	{
		Category category=categoryRepo.findById(postDto.getCategoryId()).orElseThrow(()-> new ResourceNotFoundException("Category","id",postDto.getCategoryId()));
		Post post=postRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("post","id", id));
		post.setTitle(postDto.getTitle());
		post.setCategory(category);
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		return mapToDto(post);
	}

	@Override
	public void deletePost(long id) {
		Post post=postRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("post", "id",id));
		postRepo.delete(post);
		
	}

	@Override
	public List<PostDto> getPostsByCategory(long categoryId) 
	{
		Category category=categoryRepo.findById(categoryId).orElseThrow(
				()-> new ResourceNotFoundException("Category","id",categoryId));	
		List<Post> posts=postRepo.findByCategoryId(categoryId);
		
		return posts.stream().map((p)->mapToDto(p)).collect(Collectors.toList()) ;
		
	}
}
