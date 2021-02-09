package com.example.pharmacySystem.dto;

import java.time.LocalDate;

import com.example.pharmacySystem.model.OrderForm;

public class OrderFormDto {

	private Long id;
	private LocalDate untilDate;
	private boolean deleted;
	private PharmacyAdministratorDto pharmacyAdministrator;
	
	public OrderFormDto() {
		super();
	}
	
	public OrderFormDto(OrderForm of) {
		this.id = of.getId();
		this.untilDate = of.getUntilDate();
		this.deleted = of.isDeleted();
		this.pharmacyAdministrator = new PharmacyAdministratorDto(of.getPharmacyAdministrator());
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
	
	public boolean isDeleted() {
		return deleted;
	}
	
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public PharmacyAdministratorDto getPharmacyAdministrator() {
		return pharmacyAdministrator;
	}
	
	public void setPharmacyAdministrator(PharmacyAdministratorDto pharmacyAdministrator) {
		this.pharmacyAdministrator = pharmacyAdministrator;
	}
}
