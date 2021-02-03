package com.example.pharmacySystem.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.pharmacySystem.dto.PharmacyDto;
import com.example.pharmacySystem.model.Pharmacy;
import com.example.pharmacySystem.repository.PharmacyRepository;

@Service
@Transactional(readOnly = true)
public class PharmacyService {

	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	public List<Pharmacy> findAll(){
		List<Pharmacy> allPharmacies = pharmacyRepository.findAll();
		return allPharmacies;
	}
	
	public Pharmacy findOneById(Long id) {
		Pharmacy pharmacy = pharmacyRepository.findOneById(id);	
		return pharmacy;
	}
	
	public Pharmacy findOneByName(String name) {
		Pharmacy pharmacy = pharmacyRepository.findOneByName(name);
		return pharmacy;
	}
	
	@Transactional(readOnly = false)
	public Pharmacy create(PharmacyDto pharmacy) {
		Pharmacy newPharmacy = new Pharmacy();
		newPharmacy.setName(pharmacy.getName());
		newPharmacy.setAddress(pharmacy.getAddress());
		newPharmacy.setCity(pharmacy.getCity());
		pharmacyRepository.save(newPharmacy);
		return newPharmacy;
	}
}
