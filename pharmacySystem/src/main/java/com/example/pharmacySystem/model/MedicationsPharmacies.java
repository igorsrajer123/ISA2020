package com.example.pharmacySystem.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class MedicationsPharmacies {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "price")
	private double price;
	
	@Column(name = "amount")
	private int amount;
	
	@JsonBackReference(value = "pharmacyMeds-movement")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Pharmacy pharmacy;
	
	@JsonBackReference(value = "medicationPharmacy-movement")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Medication medication;
	
	@JsonManagedReference(value = "reservationMedication-movement")
	@OneToMany(mappedBy = "medicationFromPharmacy", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<MedicationReservation> medicationReservations;
	
	@Column(name = "deleted")
	private boolean deleted;
	
	@Column(name = "priceExpiringDate")
	private LocalDate priceExpiringDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public Medication getMedication() {
		return medication;
	}

	public void setMedication(Medication medication) {
		this.medication = medication;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public List<MedicationReservation> getMedicationReservations() {
		return medicationReservations;
	}

	public void setMedicationReservations(List<MedicationReservation> medicationReservations) {
		this.medicationReservations = medicationReservations;
	}

	public LocalDate getPriceExpiringDate() {
		return priceExpiringDate;
	}

	public void setPriceExpiringDate(LocalDate priceExpiringDate) {
		this.priceExpiringDate = priceExpiringDate;
	}
}
