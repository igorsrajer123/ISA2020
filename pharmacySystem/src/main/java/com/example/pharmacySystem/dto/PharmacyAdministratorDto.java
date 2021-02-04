package com.example.pharmacySystem.dto;

import com.example.pharmacySystem.model.PharmacyAdministrator;

public class PharmacyAdministratorDto {

	private Long id;
	private UserDto user;
	private PharmacyDto pharmacyDto;
	
	public PharmacyAdministratorDto() {
		super();
	}
	
	public PharmacyAdministratorDto(Long id, UserDto user, PharmacyDto pharmacyDto) {
		super();
		this.id = id;
		this.user = user;
		this.pharmacyDto = pharmacyDto;
	}
	
	public PharmacyAdministratorDto(PharmacyAdministrator admin) {
		this.id = admin.getId();
		this.user = new UserDto(admin.getUser());
		this.pharmacyDto = new PharmacyDto(admin.getPharmacy());
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

	public PharmacyDto getPharmacyDto() {
		return pharmacyDto;
	}

	public void setPharmacyDto(PharmacyDto pharmacyDto) {
		this.pharmacyDto = pharmacyDto;
	}
}
