package com.example.blogapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blogapp.model.Category;

//all the repository interfaces are belongs to the repository package
//it extends jparepository it helps to crate some database level operations 
//and provide some methods like save,findByid,deleteByid,existById, and all 
//and we have to write inside the repository interface custom query methods.

public interface CategoryRepository extends JpaRepository<Category,Long>
{

}
