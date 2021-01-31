package com.example.pharmacySystem.dto;

import com.example.pharmacySystem.model.Medication;

public class MedicationDto {

	private Long id;
	private String name;
	private double price;
	
	public MedicationDto() {
		super();
	}

	public MedicationDto(Long id, String name, double price) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
	}
	
	public MedicationDto(Medication medication) {
		this.id = medication.getId();
		this.name = medication.getName();
		this.price = medication.getPrice();
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
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
}
