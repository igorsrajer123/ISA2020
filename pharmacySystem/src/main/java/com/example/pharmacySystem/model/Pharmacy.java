package com.example.pharmacySystem.model;

import java.util.List;
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

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
	
	@Column(name = "description", nullable = true)
	private String description;
	
	@Column(name = "rating")
	private double rating;
	
	@Column(name = "numberOfVotes")
	private int numberOfVotes;
	
	@JsonIgnoreProperties(value = {"pharmacy"}, allowSetters = true)
	@OneToMany(mappedBy = "pharmacy", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Pharmacist> pharmacists;
	
	@JsonIgnoreProperties(value = {"pharmacies"}, allowSetters = true)
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "pharmacyDermatologists", joinColumns = { @JoinColumn(name = "pharmacyId", referencedColumnName = "id")}, inverseJoinColumns = @JoinColumn(name = "dermatologistId", referencedColumnName = "id"))
	private List<Dermatologist> dermatologists;
	
	@JsonIgnoreProperties(value = {"pharmacy"}, allowSetters = true)
	@OneToMany(mappedBy = "pharmacy", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<PharmacyAdministrator> pharmacyAdministrators;
	
	@JsonIgnoreProperties("pharmacy")
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "pharmacyMedications", joinColumns = { @JoinColumn(name = "pharmacyId", referencedColumnName = "id")}, inverseJoinColumns = @JoinColumn(name = "medicationId", referencedColumnName = "id"))
	private List<Medication> medications;
	
	@JsonManagedReference
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToMany(mappedBy = "pharmacy", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<DermatologistPharmacyHours> dermatologistHours;
	
	public Pharmacy() {
		super();
	}
	
	public Pharmacy(Long id, String name, String address, String city, String description, double rating, int numberOfVotes) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.rating = rating;
		this.numberOfVotes = numberOfVotes;
		this.city = city;
		this.description = description;
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
	
	public List<Dermatologist> getDermatologists() {
		return dermatologists;
	}

	public void setDermatologists(List<Dermatologist> dermatologists) {
		this.dermatologists = dermatologists;
	}

	public List<PharmacyAdministrator> getPharmacyAdministrators() {
		return pharmacyAdministrators;
	}

	public void setPharmacyAdministrators(List<PharmacyAdministrator> pharmacyAdministrators) {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Pharmacist> getPharmacists(){
		return pharmacists;
	}
	
	public void setPharmacists(List<Pharmacist> pharmacists) {
		this.pharmacists = pharmacists;
	}

	public List<DermatologistPharmacyHours> getDermatologistHours() {
		return dermatologistHours;
	}

	public void setDermatologistHours(List<DermatologistPharmacyHours> dermatologistHours) {
		this.dermatologistHours = dermatologistHours;
	}
}
