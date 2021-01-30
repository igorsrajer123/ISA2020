package com.example.pharmacySystem.dto;

import com.example.pharmacySystem.model.Patient;

public class PatientDto {

	private Long id;
	private UserDto user;
	private String address;
	private String phoneNumber;
	private String city;
	private String country;
	
	public PatientDto() {
		super();
	}
	
	public PatientDto(Long id, UserDto user, String address, String phoneNumber) {
		super();
		this.id = id;
		this.user = user;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}
	
	public PatientDto(Patient patient) {
		this.id = patient.getId();
		this.user = new UserDto(patient.getUser());
		this.address = patient.getAddress();
		this.phoneNumber = patient.getPhoneNumber();
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
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
