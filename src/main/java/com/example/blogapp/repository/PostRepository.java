package com.example.blogapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blogapp.model.Post;
import java.util.*;
public interface PostRepository extends JpaRepository<Post,Long>
{
	List<Post> findByCategoryId(long categoryId);
}
