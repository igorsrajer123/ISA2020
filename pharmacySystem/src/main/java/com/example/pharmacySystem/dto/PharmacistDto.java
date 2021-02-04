package com.example.pharmacySystem.dto;

import com.example.pharmacySystem.model.Pharmacist;

public class PharmacistDto {

	private Long id;
	private UserDto user;
	private PharmacyDto pharmacy;
	private double rating;
	private int numberOfVotes;
	
	public PharmacistDto() {
		super();
	}
	
	public PharmacistDto(Pharmacist pharmacist) {
		this.id = pharmacist.getId();
		this.user = new UserDto(pharmacist.getUser());
		this.pharmacy = new PharmacyDto(pharmacist.getPharmacy());
		this.rating = pharmacist.getRating();
		this.numberOfVotes = pharmacist.getNumberOfVotes();
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
}
