package com.example.pharmacySystem.dto;

import com.example.pharmacySystem.model.Pharmacy;

public class PharmacyDto {

	private Long id;
	private String name;
	private String description;
	private String location;
	
	public PharmacyDto() {
		super();
	}
	
	public PharmacyDto(Long id, String name, String description, String location) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.location = location;
	}
	
	public PharmacyDto(Pharmacy pharmacy) {
		this.id = pharmacy.getId();
		this.name = pharmacy.getName();
		this.description = pharmacy.getDescription();
		this.location = pharmacy.getLocation();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
}
