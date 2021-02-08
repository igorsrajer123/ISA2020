package com.example.pharmacySystem.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Promotion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "text", nullable = false)
	private String text;
	
	@Column(name = "untilDate")
	private LocalDate untilDate;
	
	@JsonBackReference(value = "promotion-movement")
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Pharmacy pharmacy;

	public Promotion() {
		super();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public LocalDate getUntilDate() {
		return untilDate;
	}

	public void setUntilDate(LocalDate untilDate) {
		this.untilDate = untilDate;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
}
