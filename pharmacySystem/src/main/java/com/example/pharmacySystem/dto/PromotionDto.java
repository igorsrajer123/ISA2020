package com.example.pharmacySystem.dto;

import java.time.LocalDate;
import com.example.pharmacySystem.model.Promotion;

public class PromotionDto {

	private Long id;
	private String text;
	private LocalDate untilDate;
	private PharmacyDto pharmacy;
	
	public PromotionDto() {
		super();
	}
	
	public PromotionDto(Promotion p) {
		this.id = p.getId();
		this.text = p.getText();
		this.untilDate = p.getUntilDate();
		this.pharmacy = new PharmacyDto(p.getPharmacy());
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
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
	
	public PharmacyDto getPharmacy() {
		return pharmacy;
	}
	
	public void setPharmacy(PharmacyDto pharmacy) {
		this.pharmacy = pharmacy;
	}
}
