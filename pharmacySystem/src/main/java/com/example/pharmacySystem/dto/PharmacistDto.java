package com.example.pharmacySystem.dto;

import com.example.pharmacySystem.model.Pharmacist;

public class PharmacistDto {

	private Long id;
	private UserDto user;
	private PharmacyDto pharmacy;
	
	public PharmacistDto() {
		super();
	}
	
	public PharmacistDto(Long id, UserDto user, PharmacyDto pharmacy) {
		super();
		this.id = id;
		this.user = user;
		this.pharmacy = pharmacy;
	}
	
	public PharmacistDto(Pharmacist pharmacist) {
		this.id = pharmacist.getId();
		this.user = new UserDto(pharmacist.getUser());
		this.pharmacy = new PharmacyDto(pharmacist.getPharmacy());
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
