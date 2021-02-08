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
import javax.persistence.OneToOne;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonManagedReference(value = "patient-movement")
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private User user;

	@Column(name = "address", nullable = true)
	private String address;
	
	@Column(name = "phoneNumber", nullable = true)
	private String phoneNumber;
	
	@Column(name = "city", nullable = true)
	private String city;
	
	@Column(name = "country", nullable = true)
	private String country;
	
	@Column(name = "processed", nullable = false)
	private boolean processed;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "patientAllergicOn", joinColumns = @JoinColumn(name = "patientId", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "medicationId", referencedColumnName = "id"))
	private List<Medication> allergicOn;
	
	@JsonManagedReference(value = "examinationPatient-movement")
	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Examination> examinations;
	
	@JsonManagedReference(value = "reservationPatient-movement")
	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<MedicationReservation> medicationReservations;
	
	@JsonManagedReference(value = "counselingPatient-movement")
	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Counseling> counselings;
	
	public Patient() {
		super();
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	public List<Medication> getAllergicOn() {
		return allergicOn;
	}

	public void setAllergicOn(List<Medication> allergicOn) {
		this.allergicOn = allergicOn;
	}

	public List<Examination> getExaminations() {
		return examinations;
	}

	public void setExaminations(List<Examination> examinations) {
		this.examinations = examinations;
	}

	public List<MedicationReservation> getMedicationReservations() {
		return medicationReservations;
	}

	public void setMedicationReservations(List<MedicationReservation> medicationReservations) {
		this.medicationReservations = medicationReservations;
	}

	public List<Counseling> getCounselings() {
		return counselings;
	}

	public void setCounselings(List<Counseling> counselings) {
		this.counselings = counselings;
	}
}
