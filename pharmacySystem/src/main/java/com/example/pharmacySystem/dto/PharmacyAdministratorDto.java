package com.example.pharmacySystem.dto;

import com.example.pharmacySystem.model.PharmacyAdministrator;

public class PharmacyAdministratorDto {

	private Long id;
	private UserDto user;
	private Long pharmacyId;
	
	public PharmacyAdministratorDto() {
		super();
	}
	
	public PharmacyAdministratorDto(Long id, UserDto user, Long pharmacyId) {
		super();
		this.id = id;
		this.user = user;
		this.setPharmacyId(pharmacyId);
	}
	
	public PharmacyAdministratorDto(PharmacyAdministrator pharmacyAdministrator) {
		this.id = pharmacyAdministrator.getId();
		this.user = new UserDto(pharmacyAdministrator.getUser());
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

	public Long getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(Long pharmacyId) {
		this.pharmacyId = pharmacyId;
	}
}
