package com.example.pharmacySystem.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Pharmacy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "pharmacyName", nullable = false)
	private String name;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "rating")
	private double rating;
	
	@Column(name = "numberOfVotes")
	private int numberOfVotes;
	
	@OneToMany(mappedBy = "pharmacy", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Pharmacist> pharmacists;
	
	@ManyToMany(mappedBy = "pharmacies")
	private Set<Dermatologist> dermatologists;
	
	@OneToMany(mappedBy = "pharmacy", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<PharmacyAdministrator> pharmacyAdministrators;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "pharmacyMedications", joinColumns = { @JoinColumn(name = "pharmacyId", referencedColumnName = "id")}, inverseJoinColumns = @JoinColumn(name = "medicationId", referencedColumnName = "id"))
	private List<Medication> medications;
	
	public Pharmacy() {
		super();
	}
	
	public Pharmacy(Long id, String name, String address, String city, double rating, int numberOfVotes) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.rating = rating;
		this.numberOfVotes = numberOfVotes;
		this.city = city;
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

	public Set<Pharmacist> getPharmacists() {
		return pharmacists;
	}

	public void setPharmacists(Set<Pharmacist> pharmacists) {
		this.pharmacists = pharmacists;
	}
	
	public Set<Dermatologist> getDermatologists() {
		return dermatologists;
	}

	public void setDermatologists(Set<Dermatologist> dermatologists) {
		this.dermatologists = dermatologists;
	}

	public Set<PharmacyAdministrator> getPharmacyAdministrators() {
		return pharmacyAdministrators;
	}

	public void setPharmacyAdministrators(Set<PharmacyAdministrator> pharmacyAdministrators) {
		this.pharmacyAdministrators = pharmacyAdministrators;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public List<Medication> getMedications() {
		return medications;
	}

	public void setMedications(List<Medication> medications) {
		this.medications = medications;
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
}
