package com.example.pharmacySystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pharmacySystem.model.MedicationsPharmacies;
import com.example.pharmacySystem.model.MedicationsToOrder;
import com.example.pharmacySystem.model.Offer;
import com.example.pharmacySystem.model.OrderForm;
import com.example.pharmacySystem.model.Pharmacy;
import com.example.pharmacySystem.model.PharmacyAdministrator;
import com.example.pharmacySystem.repository.OfferRepository;
import com.example.pharmacySystem.repository.OrderFormRepository;
import com.example.pharmacySystem.repository.PharmacyAdministratorRepository;
import com.example.pharmacySystem.repository.PharmacyRepository;

@Service
public class OfferService {

	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
	private OrderFormRepository orderRepository;
	
	@Autowired
	private PharmacyAdministratorRepository pharmacyAdminRepository;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	public List<Offer> findAll(){
		return offerRepository.findAll();
	}
	
	public List<Offer> findAllByOrderId(Long id){
		return offerRepository.findAllByOrderId(id);
	}
	
	public Offer acceptOffer(Long id) {
		Offer offer = offerRepository.findOneById(id);
		OrderForm order = orderRepository.findOneById(offer.getOrder().getId());
		PharmacyAdministrator admin = pharmacyAdminRepository.findOneById(order.getPharmacyAdministrator().getId());
		Pharmacy pharmacy = pharmacyRepository.findOneById(admin.getPharmacy().getId());
		
		List<MedicationsPharmacies> medsInPharmacy = pharmacy.getMedicationsInPharmacy();
		List<MedicationsToOrder> medsToOrder = order.getMedicationsToOrder();
		
		for(MedicationsPharmacies m : medsInPharmacy) {
			for(MedicationsToOrder mto : medsToOrder) {
				if(mto.getMedication().getId() == m.getMedication().getId()) {
					m.setAmount(m.getAmount() + mto.getAmount());
				}
			}
		}
		
		for(Offer o : order.getOffers()) {
			if(o.getId() == offer.getId()) {
				offer.setStatus("ACCEPTED");
			}else {
				o.setStatus("DECLINED");				
			}
		}
		
		orderRepository.save(order);
		offerRepository.save(offer);
		return offer;
	}
}
