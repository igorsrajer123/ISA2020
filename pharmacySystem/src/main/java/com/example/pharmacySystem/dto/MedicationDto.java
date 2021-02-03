package com.example.pharmacySystem.dto;

import com.example.pharmacySystem.model.Medication;

public class MedicationDto {

	private Long id;
	private String name;
	private double price;
	private String code;
	private double dailyIntake;
	private String sideEffects;
	private String chemicalComposition;
	private MedicationTypeDto medicationType;
	private String substitution;
	
	public MedicationDto() {
		super();
	}

	public MedicationDto(Medication medication) {
		this.id = medication.getId();
		this.name = medication.getName();
		this.price = medication.getPrice();
		this.code = medication.getCode();
		this.dailyIntake = medication.getDailyIntake();
		this.sideEffects = medication.getSideEffects();
		this.chemicalComposition = medication.getChemicalComposition();
		this.medicationType = new MedicationTypeDto(medication.getMedicationType());
		this.setSubstitution(medication.getSubstitution());
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getDailyIntake() {
		return dailyIntake;
	}

	public void setDailyIntake(double dailyIntake) {
		this.dailyIntake = dailyIntake;
	}

	public String getSideEffects() {
		return sideEffects;
	}

	public void setSideEffects(String sideEffects) {
		this.sideEffects = sideEffects;
	}

	public MedicationTypeDto getMedicationType() {
		return medicationType;
	}

	public void setMedicationType(MedicationTypeDto medicationType) {
		this.medicationType = medicationType;
	}

	public String getChemicalComposition() {
		return chemicalComposition;
	}

	public void setChemicalComposition(String chemicalComposition) {
		this.chemicalComposition = chemicalComposition;
	}

	public String getSubstitution() {
		return substitution;
	}

	public void setSubstitution(String substitution) {
		this.substitution = substitution;
	}
}
