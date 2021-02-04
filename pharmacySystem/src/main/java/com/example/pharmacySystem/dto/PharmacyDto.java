package com.example.pharmacySystem.dto;

import com.example.pharmacySystem.model.Pharmacy;

public class PharmacyDto {

	private Long id;
	private String name;
	private String address;
	private String city;
	private String description;
	private double rating;
	private int numberOfVotes;
	
	public PharmacyDto() {
		super();
	}
	
	public PharmacyDto(Long id, String name, String address, String city, String description, double rating, int numberOfVotes) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.city = city;
		this.rating = rating;
		this.numberOfVotes = numberOfVotes;
		this.description = description;
	}
	
	public PharmacyDto(Pharmacy pharmacy) {
		this.id = pharmacy.getId();
		this.name = pharmacy.getName();
		this.address = pharmacy.getAddress();
		this.city = pharmacy.getCity();
		this.rating = pharmacy.getRating();		
		this.numberOfVotes = pharmacy.getNumberOfVotes();
		this.description = pharmacy.getDescription();
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
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public int getNumberOfVotes() {
		return numberOfVotes;
	}

	public void setNumberOfVotes(int numberOfVotes) {
		this.numberOfVotes = numberOfVotes;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
