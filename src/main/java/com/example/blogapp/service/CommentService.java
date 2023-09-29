package com.example.blogapp.service;

import java.util.List;

import com.example.blogapp.payload.CommentDto;

public interface CommentService 
{
	public CommentDto createComment(long postId,CommentDto commentDto);
	
	public List<CommentDto>  getAllComments(long postId);
	
	public CommentDto findByCommentId(long postId,long commentId);
	
	public CommentDto updateComment(long postId,long commentId,CommentDto commentReq);
	
	public void deleteComment(long postId,long commentId);

}
