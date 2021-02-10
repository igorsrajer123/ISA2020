package com.example.pharmacySystem.dto;
import java.time.LocalDate;
import com.example.pharmacySystem.model.Offer;

public class OfferDto {

	private Long id;
	private double price;
	private SupplierDto supplier;
	private OrderFormDto order;
	private LocalDate dateOfDelivery;
	private String status;
	
	public OfferDto() {
		super();
	}
	
	public OfferDto(Offer o) {
		this.id = o.getId();
		this.price = o.getPrice();
		this.supplier = new SupplierDto(o.getSupplier());
		this.order = new OrderFormDto(o.getOrder());
		this.dateOfDelivery = o.getDateOfDelivery();
		this.status = o.getStatus();
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
	
	public SupplierDto getSupplier() {
		return supplier;
	}
	
	public void setSupplier(SupplierDto supplier) {
		this.supplier = supplier;
	}
	
	public OrderFormDto getOrder() {
		return order;
	}
	
	public void setOrder(OrderFormDto order) {
		this.order = order;
	}
	
	public LocalDate getDateOfDelivery() {
		return dateOfDelivery;
	}
	
	public void setDateOfDelivery(LocalDate dateOfDelivery) {
		this.dateOfDelivery = dateOfDelivery;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
}
