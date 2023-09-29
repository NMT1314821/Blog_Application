package com.example.blogapp.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//all DTO classes are belongs to the payload package the working of this classes
//are transfer data to the client and server it reduces remote calls between client and server
//and transfer sucure data like passwords and usernames
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto 
{
	private long id;
	private String name;
	private String description;
	

}
