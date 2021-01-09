package com.example.pharmacySystem.dto;

import com.example.pharmacySystem.model.Dermatologist;

public class DermatologistDto {

	private Long id;
	private UserDto user;
	
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
}
