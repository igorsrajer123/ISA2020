package com.example.pharmacySystem.dto;

import java.time.LocalDate;

import com.example.pharmacySystem.model.MedicationsPharmacies;

public class MedicationsPharmaciesDto {

	private Long id;
	private double price;
	private int amount;
	private PharmacyDto pharmacy;
	private MedicationDto medication;
	private boolean deleted;
	private LocalDate priceExpiringDate;
	
	public MedicationsPharmaciesDto() {
		super();
	}
	
	public MedicationsPharmaciesDto(MedicationsPharmacies m) {
		this.id = m.getId();
		this.price = m.getPrice();
		this.amount = m.getAmount();
		this.pharmacy = new PharmacyDto(m.getPharmacy());
		this.medication = new MedicationDto(m.getMedication());
		this.deleted = m.isDeleted();
		this.priceExpiringDate = m.getPriceExpiringDate();
	}
	
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
	
	public PharmacyDto getPharmacy() {
		return pharmacy;
	}
	
	public void setPharmacy(PharmacyDto pharmacy) {
		this.pharmacy = pharmacy;
	}
	
	public MedicationDto getMedication() {
		return medication;
	}
	
	public void setMedication(MedicationDto medication) {
		this.medication = medication;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public LocalDate getPriceExpiringDate() {
		return priceExpiringDate;
	}

	public void setPriceExpiringDate(LocalDate priceExpiringDate) {
		this.priceExpiringDate = priceExpiringDate;
	}
}
