package com.example.pharmacySystem.model;
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Medication {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "code", nullable = false)
	private String code;
	
	@Column(name = "dailyIntake", nullable = false)
	private double dailyIntake;
	
	@Column(name = "sideEffects", nullable = true)
	private String sideEffects;
	
	@Column(name = "chemicalComposition", nullable = false)
	private String chemicalComposition;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "substitution", nullable = true)
	private String substitution;
	
	@JsonIgnoreProperties("medications")
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private MedicationType medicationType;
	
	@JsonManagedReference(value = "medicationPharmacy-movement")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToMany(mappedBy = "medication", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<MedicationsPharmacies> medicationsInPharmacy;

	public Medication() {
		super();
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

	public MedicationType getMedicationType() {
		return medicationType;
	}

	public void setMedicationType(MedicationType medicationType) {
		this.medicationType = medicationType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getDailyIntake() {
		return dailyIntake;
	}

	public void setDailyIntake(double dailyIntake) {
		this.dailyIntake = dailyIntake;
	}

	public String getSideEffects() {
		return sideEffects;
	}

	public void setSideEffects(String sideEffects) {
		this.sideEffects = sideEffects;
	}

	public String getChemicalComposition() {
		return chemicalComposition;
	}

	public void setChemicalComposition(String chemicalComposition) {
		this.chemicalComposition = chemicalComposition;
	}

	public String getSubstitution() {
		return substitution;
	}

	public void setSubstitution(String substitution) {
		this.substitution = substitution;
	}

	public List<MedicationsPharmacies> getMedicationsInPharmacy() {
		return medicationsInPharmacy;
	}

	public void setMedicationsInPharmacy(List<MedicationsPharmacies> medicationsInPharmacy) {
		this.medicationsInPharmacy = medicationsInPharmacy;
	}
}
