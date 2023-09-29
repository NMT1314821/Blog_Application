package com.example.blogapp.serviceimpl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.blogapp.exception.BlogAppApiException;
import com.example.blogapp.exception.ResourceNotFoundException;
import com.example.blogapp.model.Comment;
import com.example.blogapp.model.Post;
import com.example.blogapp.payload.CommentDto;
import com.example.blogapp.repository.CommentRepository;
import com.example.blogapp.repository.PostRepository;
import com.example.blogapp.service.CommentService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService
{
	@Autowired
	private CommentRepository commentRepo;
	
	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public CommentDto createComment(long postId,CommentDto commentDto) {
		//dto to jpa
		Comment comment=mapToEntity(commentDto);
		// retrive post by id
		
		Post post=postRepo.findById(postId).orElseThrow(
				()-> new ResourceNotFoundException("Post","id", postId));
		//set post to comment entity
		comment.setPost(post);
		//save enity to db;
		Comment newcom=commentRepo.save(comment); 
		return mapToDto(newcom);
				
	}
	private CommentDto mapToDto(Comment comment)
	{
		
		CommentDto commentDto=mapper.map(comment,CommentDto.class);
		return commentDto;
		
		//CommentDto commentDto=new CommentDto();
		
		
		
//		commentDto.setId(comment.getId());
//		commentDto.setName(comment.getName());
//		commentDto.setEmail(comment.getEmail());
//		commentDto.setBody(comment.getBody());
//		return commentDto;
	}
	private Comment mapToEntity(CommentDto commentDto)
	{
		Comment comment=mapper.map(commentDto,Comment.class);
		return comment;
		
//		Comment comment=new Comment();
//		comment.setId(commentDto.getId());
//		comment.setName(commentDto.getName());
//		comment.setEmail(commentDto.getEmail());
//		comment.setBody(commentDto.getBody());
//		return comment;
	}
	@Override
	public List<CommentDto> getAllComments(long postId) 
	{
		List<Comment> comments=commentRepo.findByPostId(postId);
		
		return comments.stream().map((s)->mapToDto(s)).collect(Collectors.toList());
	}
	@Override
	public CommentDto findByCommentId(long postId, long commentId) {
		
		Post post=postRepo.findById(postId).orElseThrow(
				()->new ResourceNotFoundException("Post","id",postId));
		Comment comment=commentRepo.findById(commentId).orElseThrow(
				()-> new ResourceNotFoundException("Comment","id", commentId));
		
		if(!comment.getPost().getId().equals(post.getId()))
		{
			throw new BlogAppApiException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
		}
		return mapToDto(comment);
	}
	@Override
	public CommentDto updateComment(long postId, long commentId, CommentDto commentReq) {
		
		Post post=postRepo.findById(postId).orElseThrow(
				()-> new ResourceNotFoundException("Post","id",postId));
		
		
		Comment comment=commentRepo.findById(commentId).orElseThrow(
				()-> new ResourceNotFoundException("Comment","id", commentId));
		
		if(!comment.getPost().getId().equals(comment.getId()))
		{
			throw new BlogAppApiException(HttpStatus.BAD_REQUEST,"Comment does't belongs to post");
		}

		comment.setName(commentReq.getName());
		comment.setEmail(commentReq.getEmail());
		comment.setBody(commentReq.getBody());
		
		Comment newcomment=commentRepo.save(comment);
		return mapToDto(newcomment);
	}
	@Override
	public void deleteComment(long postId, long commentId) {
		
		Post post=postRepo.findById(postId).orElseThrow(
				()-> new ResourceNotFoundException("Post","id",postId));
		
		
		Comment comment=commentRepo.findById(commentId).orElseThrow(
				()-> new ResourceNotFoundException("Comment","id", commentId));
		
		if(!comment.getPost().getId().equals(comment.getId()))
		{
			throw new BlogAppApiException(HttpStatus.BAD_REQUEST,"Comment does't belongs to post");
		}
		commentRepo.delete(comment);
	}
	
	
	
}
