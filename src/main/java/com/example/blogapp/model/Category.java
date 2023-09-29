package com.example.blogapp.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//this class is an entity it's created attributes and all in our database table
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="categories")
public class Category 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;

	//implementing onetomany and manytoone bidirectional mapping;
	
	@OneToMany(mappedBy ="category",cascade = CascadeType.ALL,orphanRemoval = true)
	Set<Post> posts=new HashSet<>();
}
