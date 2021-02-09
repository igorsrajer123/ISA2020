package com.example.pharmacySystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pharmacySystem.dto.OrderFormDto;
import com.example.pharmacySystem.model.OrderForm;
import com.example.pharmacySystem.repository.OrderFormRepository;
import com.example.pharmacySystem.repository.PharmacyAdministratorRepository;

@Service
public class OrderFormService {

	@Autowired
	private OrderFormRepository orderRepository;
	
	@Autowired
	private PharmacyAdministratorRepository adminRepository;
	
	public List<OrderForm> findAll(){
		return orderRepository.findAll();
	}
	
	public OrderForm findOneById(Long id) {
		return orderRepository.findOneById(id);
	}
	
	public List<OrderForm> findAllByPharmacyAdministratorId(Long id){
		return orderRepository.findAllByPharmacyAdministratorId(id);
	}
	
	public OrderForm createOrder(OrderFormDto orderDto) {
		OrderForm newOrder = new OrderForm();
		newOrder.setDeleted(false);
		newOrder.setUntilDate(orderDto.getUntilDate());
		newOrder.setPharmacyAdministrator(adminRepository.findOneById(orderDto.getPharmacyAdministrator().getId()));
		orderRepository.save(newOrder);
		return newOrder;
	}

	
	
}
