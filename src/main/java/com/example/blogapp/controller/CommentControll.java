package com.example.blogapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.blogapp.payload.CommentDto;
import com.example.blogapp.service.CommentService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class CommentControll
{
	@Autowired
	private CommentService commentServ;
	
	@PostMapping("/posts/{postId}/comments")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CommentDto> createComment(@PathVariable(value="postId") long postId,@Valid @RequestBody CommentDto commentDto)
	{
		return new ResponseEntity<>(commentServ.createComment(postId,commentDto),HttpStatus.CREATED);
	}
	
	@GetMapping("posts/{postId}/comments")
	public ResponseEntity<List<CommentDto>> getAllComments(@PathVariable(value="postId")long postId)
	{
		return new ResponseEntity<>(commentServ.getAllComments(postId),HttpStatus.OK);
	}
	
	@GetMapping("posts/{postId}/comments/{id}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable(value="postId")long postId,
													@PathVariable(value="id")long commentId)
	{
		return new ResponseEntity<>(commentServ.findByCommentId(postId, commentId),HttpStatus.OK);
	}
	
	@PutMapping("posts/{postId}/comments/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CommentDto> updateComment(@PathVariable(value="postId")Long postId,
													@PathVariable(value="id") Long commentId,
													@Valid @RequestBody CommentDto commentReq)
	{
		return new ResponseEntity<>(commentServ.updateComment(postId,commentId,commentReq),HttpStatus.OK);
	}

	@DeleteMapping("posts/{postId}/comments/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String>deleteComment(@PathVariable(value="postId") long postID,
											   @PathVariable(value="id") long commentId)
	{
		commentServ.deleteComment(postID, commentId);
		return new ResponseEntity<>("deleted successfully",HttpStatus.OK);
	}
	
	
	
	
	
	
	
}
