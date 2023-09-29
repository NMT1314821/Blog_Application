package com.example.blogapp.payload;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class ErrorDetails 
{
	
	private Date tiemstamp;
	private String message;
	private String details;
	

	public ErrorDetails(Date tiemstamp, String message, String details) {
		super();
		this.tiemstamp = tiemstamp;
		this.message = message;
		this.details = details;
	}
	


	public Date getTiemstamp() {
		return tiemstamp;
	}
	public void setTiemstamp(Date tiemstamp) {
		this.tiemstamp = tiemstamp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}

}
