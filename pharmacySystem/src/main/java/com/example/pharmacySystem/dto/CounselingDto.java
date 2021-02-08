package com.example.pharmacySystem.dto;

import java.time.LocalDate;

import com.example.pharmacySystem.model.Counseling;


public class CounselingDto {
	
	private Long id;
	private String status;
	private LocalDate date;	
	private int from;
	private int to;
	private PatientDto patient;
	private PharmacistDto pharmacist;
	
	public CounselingDto() {
		super();
	}
	
	public CounselingDto(Counseling c) {
		this.id = c.getId();
		this.status = c.getStatus();
		this.date = c.getDate();
		this.from = c.getFrom();
		this.to = c.getTo();
		this.patient = new PatientDto(c.getPatient());
		this.pharmacist = new PharmacistDto(c.getPharmacist());
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
	
	public PatientDto getPatient() {
		return patient;
	}
	
	public void setPatient(PatientDto patient) {
		this.patient = patient;
	}
	
	public PharmacistDto getPharmacist() {
		return pharmacist;
	}
	
	public void setPharmacist(PharmacistDto pharmacist) {
		this.pharmacist = pharmacist;
	}
}
