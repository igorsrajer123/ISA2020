package com.example.pharmacySystem.dto;

import com.example.pharmacySystem.model.PharmacyAdministrator;

public class PharmacyAdministratorDto {

	private Long id;
	private UserDto user;
	private PharmacyDto pharmacy;
	
	public PharmacyAdministratorDto() {
		super();
	}
	
	public PharmacyAdministratorDto(Long id, UserDto user, PharmacyDto pharmacy) {
		super();
		this.id = id;
		this.user = user;
		this.pharmacy = pharmacy;
	}
	
	public PharmacyAdministratorDto(PharmacyAdministrator pharmacyAdministrator) {
		this.id = pharmacyAdministrator.getId();
		this.user = new UserDto(pharmacyAdministrator.getUser());
		this.pharmacy = new PharmacyDto(pharmacyAdministrator.getPharmacy());
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
	public PharmacyDto getPharmacy() {
		return pharmacy;
	}
	public void setPharmacy(PharmacyDto pharmacy) {
		this.pharmacy = pharmacy;
	}
	
	
}
