package com.example.pharmacySystem.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Pharmacist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonIgnoreProperties("pharmacists")
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Pharmacy pharmacy;
	
	@JsonManagedReference(value = "pharmacist-movement")
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private User user;
	
	@Column(name = "rating")
	private double rating;
	
	@Column(name = "numberOfVotes")
	private int numberOfVotes;
	
	@Column(name = "deleted")
	private boolean deleted;
	
	@Column(name = "workingFrom")
	private int from;
	
	@Column(name = "workingTo")
	private int to;
	
	public Pharmacist() {
		super();
	}

	public Pharmacist(Long id, User user) {
		super();
		this.id = id;
		this.user = user;
	}
	
	public Pharmacist(Pharmacist pharmacist) {
		this.id = pharmacist.id;
		this.pharmacy = pharmacist.pharmacy;
		this.user = pharmacist.user;
		this.rating = pharmacist.rating;
		this.numberOfVotes = pharmacist.numberOfVotes;
		this.deleted = pharmacist.deleted;
		this.from = pharmacist.from;
		this.to = pharmacist.to;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}
}
