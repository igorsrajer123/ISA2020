package com.example.pharmacySystem.dto;

import com.example.pharmacySystem.model.Dermatologist;

public class DermatologistDto {

	private Long id;
	private UserDto user;
	private double rating;
	private int numberOfVotes;
	
	public DermatologistDto() {
		super();
	}
	
	public DermatologistDto(Long id, UserDto user) {
		super();
		this.id = id;
		this.user = user;
	}
	
	public DermatologistDto(Dermatologist dermatologist) {
		this.id = dermatologist.getId();
		this.user = new UserDto(dermatologist.getUser());
		this.rating = dermatologist.getRating();
		this.numberOfVotes = dermatologist.getNumberOfVotes();
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public UserDto getUser() {
		return user;
	}
	
	public void setUser(UserDto user) {
		this.user = user;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public int getNumberOfVotes() {
		return numberOfVotes;
	}

	public void setNumberOfVotes(int numberOfVotes) {
		this.numberOfVotes = numberOfVotes;
	}
}
