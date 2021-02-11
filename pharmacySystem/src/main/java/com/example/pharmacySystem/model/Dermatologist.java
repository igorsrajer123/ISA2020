package com.example.pharmacySystem.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Dermatologist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonIgnoreProperties(value = {"dermatologists"}, allowSetters = true)
	@ManyToMany(mappedBy = "dermatologists")
	private List<Pharmacy> pharmacies;
	
	@JsonManagedReference(value = "dermatologist-movement")
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private User user;
	
	@JsonManagedReference("dermatologistHours-movement")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToMany(mappedBy = "dermatologist", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<DermatologistPharmacyHours> pharmacyHours;
	
	@JsonManagedReference(value = "examinationDermatologist-movement")
	@OneToMany(mappedBy = "dermatologist", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Examination> examinations;
	
	@JsonManagedReference(value = "dermatologistAbsence-movement")
	@OneToMany(mappedBy = "dermatologist", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Absence> absences;
	
	@Column(name = "rating")
	private double rating;
	
	@Column(name = "numberOfVotes")
	private int numberOfVotes;
	
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
		this.rating = dermatologist.rating;
		this.numberOfVotes = dermatologist.numberOfVotes;
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

	public List<DermatologistPharmacyHours> getPharmacyHours() {
		return pharmacyHours;
	}

	public void setPharmacyHours(List<DermatologistPharmacyHours> pharmacyHours) {
		this.pharmacyHours = pharmacyHours;
	}

	public List<Examination> getExaminations() {
		return examinations;
	}

	public void setExaminations(List<Examination> examinations) {
		this.examinations = examinations;
	}

	public List<Absence> getAbsences() {
		return absences;
	}

	public void setAbsences(List<Absence> absences) {
		this.absences = absences;
	}
}
