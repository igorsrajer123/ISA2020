package com.example.pharmacySystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pharmacySystem.dto.OrderFormDto;
import com.example.pharmacySystem.model.MedicationsToOrder;
import com.example.pharmacySystem.model.Offer;
import com.example.pharmacySystem.model.OrderForm;
import com.example.pharmacySystem.model.Pharmacy;
import com.example.pharmacySystem.model.PharmacyAdministrator;
import com.example.pharmacySystem.model.Supplier;
import com.example.pharmacySystem.repository.OfferRepository;
import com.example.pharmacySystem.repository.OrderFormRepository;
import com.example.pharmacySystem.repository.PharmacyAdministratorRepository;
import com.example.pharmacySystem.repository.PharmacyRepository;
import com.example.pharmacySystem.repository.SupplierRepository;

@Service
public class OrderFormService {

	@Autowired
	private OrderFormRepository orderRepository;
	
	@Autowired
	private PharmacyAdministratorRepository adminRepository;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private SupplierRepository supplierRepository;
	
	@Autowired
	private OfferRepository offerRepository;
	
	public List<OrderForm> findAll(){
		return orderRepository.findAll();
	}
	
	public OrderForm findOneById(Long id) {
		return orderRepository.findOneById(id);
	}
	
	public List<OrderForm> findAllByPharmacyAdministratorIdAndDeleted(Long id, boolean deleted){
		return orderRepository.findAllByPharmacyAdministratorIdAndDeleted(id, deleted);
	}
	
	public OrderForm createOrder(OrderFormDto orderDto) {
		OrderForm newOrder = new OrderForm();
		newOrder.setDeleted(false);
		newOrder.setUntilDate(orderDto.getUntilDate());
		newOrder.setPharmacyAdministrator(adminRepository.findOneById(orderDto.getPharmacyAdministrator().getId()));
		List<Offer> orderOffers = new ArrayList<Offer>();
		newOrder.setOffers(orderOffers);
		
		if(Math.random() > 0.4) {
			Offer newOffer = new Offer();
			newOffer.setPrice(17000);
			newOffer.setDateOfDelivery(orderDto.getUntilDate().plusDays(15));
			newOffer.setStatus("ACTIVE");
			newOffer.setOrder(newOrder);
			Supplier supplier1 = supplierRepository.findOneById(Long.valueOf(1));
			newOffer.setSupplier(supplier1);
			
			Offer newOffer2 = new Offer();
			newOffer2.setPrice(25000);
			newOffer2.setDateOfDelivery(orderDto.getUntilDate().plusDays(7));
			newOffer2.setStatus("ACTIVE");
			newOffer2.setOrder(newOrder);
			Supplier supplier2 = supplierRepository.findOneById(Long.valueOf(2));
			newOffer2.setSupplier(supplier2);
			
			Offer newOffer3 = new Offer();
			newOffer3.setPrice(3000);
			newOffer3.setDateOfDelivery(orderDto.getUntilDate().plusDays(5));
			newOffer3.setStatus("ACTIVE");
			newOffer3.setOrder(newOrder);
			Supplier supplier3 = supplierRepository.findOneById(Long.valueOf(3));
			newOffer3.setSupplier(supplier3);
			
			newOrder.getOffers().add(newOffer);
			newOrder.getOffers().add(newOffer2);
			newOrder.getOffers().add(newOffer3);
			offerRepository.save(newOffer);
			offerRepository.save(newOffer2);
			offerRepository.save(newOffer3);
		}
		
		orderRepository.save(newOrder);
		return newOrder;
	}

	public List<OrderForm> getPharmacyOrders(Long pharmacyId){
		Pharmacy pharmacy = pharmacyRepository.findOneById(pharmacyId);
		List<OrderForm> pharmacyOrders = new ArrayList<OrderForm>();
		
		for(PharmacyAdministrator a : pharmacy.getPharmacyAdministrators())
			for(OrderForm o : a.getOrderForms())
				pharmacyOrders.add(o);
		
		return pharmacyOrders;
	}
	
	public OrderForm deleteOrder(Long orderId) {
		OrderForm order = orderRepository.findOneById(orderId);
		order.setDeleted(true);
		List<MedicationsToOrder> orderItems = order.getMedicationsToOrder();
		for(MedicationsToOrder mto : orderItems) {
			mto.setDeleted(true);
		}
		
		orderRepository.save(order);
		return order;
	}
	
	public OrderForm updateOrder(OrderFormDto orderDto) {
		OrderForm order = orderRepository.findOneById(orderDto.getId());
		order.setDeleted(true);
		//order.setPharmacyAdministrator(null);
		
		for(MedicationsToOrder mto : order.getMedicationsToOrder())
			mto.setDeleted(true);
		
		OrderForm newOrder = new OrderForm();
		newOrder.setDeleted(false);
		newOrder.setUntilDate(orderDto.getUntilDate());
		newOrder.setPharmacyAdministrator(adminRepository.findOneById(orderDto.getPharmacyAdministrator().getId()));
		orderRepository.save(order);
		orderRepository.save(newOrder);
		return newOrder;
	}
	
	
}
