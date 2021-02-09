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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class OrderForm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "untilDate")
	private LocalDate untilDate;
	
	@JsonManagedReference(value = "orderMedication-movement")
	@OneToMany(mappedBy = "orderForm", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<MedicationsToOrder> medicationsToOrder;
	
	@Column(name = "deleted")
	private boolean deleted;
	
	@JsonBackReference(value = "orderAdmin-movement")
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private PharmacyAdministrator pharmacyAdministrator;

	public OrderForm() {
		super();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getUntilDate() {
		return untilDate;
	}

	public void setUntilDate(LocalDate untilDate) {
		this.untilDate = untilDate;
	}

	public List<MedicationsToOrder> getMedicationsToOrder() {
		return medicationsToOrder;
	}

	public void setMedicationsToOrder(List<MedicationsToOrder> medicationsToOrder) {
		this.medicationsToOrder = medicationsToOrder;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public PharmacyAdministrator getPharmacyAdministrator() {
		return pharmacyAdministrator;
	}

	public void setPharmacyAdministrator(PharmacyAdministrator pharmacyAdministrator) {
		this.pharmacyAdministrator = pharmacyAdministrator;
	}
}
