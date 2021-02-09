package com.example.pharmacySystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pharmacySystem.dto.MedicationsToOrderDto;
import com.example.pharmacySystem.model.Medication;
import com.example.pharmacySystem.model.MedicationsPharmacies;
import com.example.pharmacySystem.model.MedicationsToOrder;
import com.example.pharmacySystem.model.OrderForm;
import com.example.pharmacySystem.model.Pharmacy;
import com.example.pharmacySystem.model.PharmacyAdministrator;
import com.example.pharmacySystem.repository.MedicationRepository;
import com.example.pharmacySystem.repository.MedicationsPharmaciesRepository;
import com.example.pharmacySystem.repository.MedicationsToOrderRepository;
import com.example.pharmacySystem.repository.OrderFormRepository;
import com.example.pharmacySystem.repository.PharmacyAdministratorRepository;
import com.example.pharmacySystem.repository.PharmacyRepository;

@Service
public class MedicationsToOrderService {

	@Autowired
	private MedicationsToOrderRepository medsToOrderRepository;
	
	@Autowired
	private OrderFormRepository orderRepository;
	
	@Autowired
	private MedicationRepository medicationRepository;
	
	@Autowired
	private PharmacyAdministratorRepository adminRepository;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private MedicationsPharmaciesRepository medsInPharmacies;
	
	public List<MedicationsToOrder> findAll(){
		return medsToOrderRepository.findAll();
	}
	
	public MedicationsToOrder findOneById(Long id) {
		return medsToOrderRepository.findOneById(id);
	}
	
	public List<MedicationsToOrder> findAllByOrderFormId(Long id){
		return medsToOrderRepository.findAllByOrderFormId(id);
	}
	
	public MedicationsToOrder createOrderItem(MedicationsToOrderDto orderItem) {
		Medication ourMed = medicationRepository.findOneById(orderItem.getMedication().getId());
		OrderForm order = orderRepository.findOneById(orderItem.getOrderForm().getId());
		PharmacyAdministrator ourAdmin = adminRepository.findOneById(order.getPharmacyAdministrator().getId());
		Pharmacy ourPharmacy = pharmacyRepository.findOneById(ourAdmin.getPharmacy().getId());
		
		boolean foundIt = false;
		for(MedicationsPharmacies m : ourPharmacy.getMedicationsInPharmacy()) {
			Medication medication = medicationRepository.findOneById(m.getMedication().getId());
			if(medication.getId() == ourMed.getId()) {
				foundIt = true;
			}
		}
		
		System.out.println(foundIt);
		if(!foundIt) {
			MedicationsPharmacies newMedPharmacy = new MedicationsPharmacies();
			newMedPharmacy.setAmount(0);
			newMedPharmacy.setDeleted(false);
			newMedPharmacy.setMedication(ourMed);
			newMedPharmacy.setPharmacy(ourPharmacy);
			newMedPharmacy.setPrice(0);
			ourPharmacy.getMedicationsInPharmacy().add(newMedPharmacy);
			ourMed.getMedicationsInPharmacy().add(newMedPharmacy);
			medsInPharmacies.save(newMedPharmacy);
		//	pharmacyRepository.save(ourPharmacy);
		//	medicationRepository.save(ourMed);
		}
		
		MedicationsToOrder newOrderItem = new MedicationsToOrder();
		newOrderItem.setAmount(orderItem.getAmount());
		newOrderItem.setDeleted(false);
		newOrderItem.setOrderForm(orderRepository.findOneById(orderItem.getOrderForm().getId()));
		newOrderItem.setMedication(medicationRepository.findOneById(orderItem.getMedication().getId()));
		medsToOrderRepository.save(newOrderItem);
		return newOrderItem;
	}
	
}
