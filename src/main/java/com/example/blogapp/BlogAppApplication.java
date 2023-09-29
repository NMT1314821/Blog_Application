package com.example.blogapp;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(
		info=@Info(
				title ="SpringBoot BlogApp REST API's",
				description = "springBoot Rest Apis",
				version = "v1.0",
				contact = @Contact(
						name="naveen",
						email="navi131@gmail.com",
						url="github.com/NMT1314821"
						),
				license = @License(
						name="apache 2.0",
						url="nat"
						)
				),
		externalDocs = @ExternalDocumentation(
				description = "nmt131",
				url="github.com"
				)
		
				
		)
public class BlogAppApplication 
{
	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApplication.class, args);
	}

}
