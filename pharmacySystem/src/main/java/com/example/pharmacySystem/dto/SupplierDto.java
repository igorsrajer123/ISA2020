package com.example.pharmacySystem.dto;

import com.example.pharmacySystem.model.Supplier;

public class SupplierDto {

	private Long id;
	private UserDto user;

	public SupplierDto() {
		super();
	}
	
	public SupplierDto(Supplier s) {
		this.id = s.getId();
		this.user = new UserDto(s.getUser());
	}
	
	public SupplierDto(Long id) {
		super();
		this.id = id;
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
