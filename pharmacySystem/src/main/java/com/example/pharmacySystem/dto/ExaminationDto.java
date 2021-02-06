package com.example.pharmacySystem.dto;

import java.time.LocalDate;

import com.example.pharmacySystem.model.Examination;


public class ExaminationDto {

	Long id;
	private String status;
	private LocalDate date;
	private String time;
	private double duration;
	private double price;
	private PharmacyDto pharmacy;
	private DermatologistDto dermatologist;
	
	public ExaminationDto() {
		super();
	}
	
	public ExaminationDto(Examination ex) {
		this.id = ex.getId();
		this.status = ex.getStatus();
		this.date = ex.getDate();
		this.time = ex.getTime();
		this.duration = ex.getDuration();
		this.price = ex.getPrice();
		this.pharmacy = new PharmacyDto(ex.getPharmacy());
		this.dermatologist = new DermatologistDto(ex.getDermatologist());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public PharmacyDto getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(PharmacyDto pharmacy) {
		this.pharmacy = pharmacy;
	}

	public DermatologistDto getDermatologist() {
		return dermatologist;
	}

	public void setDermatologist(DermatologistDto dermatologist) {
		this.dermatologist = dermatologist;
	}
}
