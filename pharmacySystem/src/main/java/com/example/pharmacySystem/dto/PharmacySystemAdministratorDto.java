package com.example.pharmacySystem.dto;

import com.example.pharmacySystem.model.PharmacySystemAdministrator;

public class PharmacySystemAdministratorDto {

	private Long id;
	private UserDto user;
	
	public PharmacySystemAdministratorDto() {
		super();
	}
	
	public PharmacySystemAdministratorDto(Long id, UserDto user) {
		super();
		this.id = id;
		this.user = user;
	}

	public PharmacySystemAdministratorDto(PharmacySystemAdministrator admin) {
		this.id = admin.getId();
		this.user =  new UserDto(admin.getUser());
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
