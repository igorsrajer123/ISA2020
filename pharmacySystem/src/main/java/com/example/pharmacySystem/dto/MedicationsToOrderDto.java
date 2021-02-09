package com.example.pharmacySystem.dto;
import com.example.pharmacySystem.model.MedicationsToOrder;


public class MedicationsToOrderDto {
	
	private Long id;
	private int amount;
	private MedicationDto medication;
	private OrderFormDto orderForm;
	private boolean deleted;
	
	public MedicationsToOrderDto() {
		super();
	}
	
	public MedicationsToOrderDto(MedicationsToOrder mto) {
		this.setId(mto.getId());
		this.amount = mto.getAmount();
		this.medication = new MedicationDto(mto.getMedication());
		this.orderForm = new OrderFormDto(mto.getOrderForm());
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public MedicationDto getMedication() {
		return medication;
	}
	
	public void setMedication(MedicationDto medication) {
		this.medication = medication;
	}
	
	public OrderFormDto getOrderForm() {
		return orderForm;
	}
	
	public void setOrderForm(OrderFormDto orderForm) {
		this.orderForm = orderForm;
	}
	
	public boolean isDeleted() {
		return deleted;
	}
	
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
