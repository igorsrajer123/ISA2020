package com.example.pharmacySystem.dto;

import com.example.pharmacySystem.model.Supplier;

public class SupplierDto {

	private Long id;

	public SupplierDto() {
		super();
	}
	
	public SupplierDto(Long id) {
		super();
		this.id = id;
	}
	
	public SupplierDto(Supplier supplier) {
		this.id = supplier.getId();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
