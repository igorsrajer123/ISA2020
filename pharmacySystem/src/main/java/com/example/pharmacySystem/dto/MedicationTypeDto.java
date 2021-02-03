package com.example.pharmacySystem.dto;

import com.example.pharmacySystem.model.MedicationType;

public class MedicationTypeDto {

	private Long id;
	private String name;
	
	public MedicationTypeDto() {
		super();
	}
	
	public MedicationTypeDto(MedicationType m) {
		this.id = m.getId();
		this.name = m.getName();
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
}
