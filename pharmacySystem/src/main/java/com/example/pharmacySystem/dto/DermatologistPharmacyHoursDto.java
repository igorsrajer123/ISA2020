package com.example.pharmacySystem.dto;

import com.example.pharmacySystem.model.DermatologistPharmacyHours;

public class DermatologistPharmacyHoursDto {

	private Long id;
	private int from;
	private int to;
	private DermatologistDto dermatologist;
	private PharmacyDto pharmacy;
	private boolean deleted;
	
	public DermatologistPharmacyHoursDto() {
		super();
	}
	
	public DermatologistPharmacyHoursDto(DermatologistPharmacyHours dto) {
		this.id = dto.getId();
		this.from = dto.getFrom();
		this.to = dto.getTo();
		this.dermatologist = new DermatologistDto(dto.getDermatologist());
		this.pharmacy = new PharmacyDto(dto.getPharmacy());
		this.deleted = dto.isDeleted();
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public DermatologistDto getDermatologist() {
		return dermatologist;
	}

	public void setDermatologist(DermatologistDto dermatologist) {
		this.dermatologist = dermatologist;
	}

	public PharmacyDto getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(PharmacyDto pharmacy) {
		this.pharmacy = pharmacy;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
