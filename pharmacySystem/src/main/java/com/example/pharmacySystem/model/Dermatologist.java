package com.example.pharmacySystem.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Dermatologist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonIgnoreProperties(value = {"dermatologists"}, allowSetters = true)
	@ManyToMany(mappedBy = "dermatologists")
	private List<Pharmacy> pharmacies;
	
	@JsonIgnoreProperties("dermatologist")
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private User user;
	
	public Dermatologist(){
		super();
	}
	
	public Dermatologist(Long id, User user) {
		super();
		this.id = id;
		this.user = user;
	}
	
	public Dermatologist(Dermatologist dermatologist){
		this.id = dermatologist.id;
		this.pharmacies = dermatologist.pharmacies;
		this.user = dermatologist.user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Pharmacy> getPharmacies() {
		return pharmacies;
	}

	public void setPharmacies(List<Pharmacy> pharmacies) {
		this.pharmacies = pharmacies;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
