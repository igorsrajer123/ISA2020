package com.example.pharmacySystem.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Dermatologist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToMany
	@JoinTable(name = "dermatologistsPharmacies", joinColumns = @JoinColumn(name = "dermatologistId", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "pharmacyId", referencedColumnName = "id"))
	private Set<Pharmacy> pharmacies;
	
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

	public Set<Pharmacy> getPharmacies() {
		return pharmacies;
	}

	public void setPharmacies(Set<Pharmacy> pharmacies) {
		this.pharmacies = pharmacies;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
