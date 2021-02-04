package com.example.pharmacySystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pharmacySystem.model.Pharmacist;
import com.example.pharmacySystem.model.Pharmacy;
import com.example.pharmacySystem.repository.PharmacistRepository;
import com.example.pharmacySystem.repository.PharmacyRepository;

@Service
public class PharmacistService {

	@Autowired
	private PharmacistRepository pharmacistRepository;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	public List<Pharmacist> findAll(){
		return pharmacistRepository.findAll();
	}
	
	public Pharmacist findOneById(Long id) {
		return pharmacistRepository.findOneById(id);
	}
	
	public List<Pharmacist> getPharmacyPharmacists(Long pharmacyId){
		Pharmacy pharmacy = pharmacyRepository.findOneById(pharmacyId);
		
		List<Pharmacist> pharmacists = pharmacy.getPharmacists();
		return pharmacists;
	}
}
