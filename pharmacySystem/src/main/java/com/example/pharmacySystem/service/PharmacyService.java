package com.example.pharmacySystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pharmacySystem.dto.PharmacyDto;
import com.example.pharmacySystem.model.Pharmacy;
import com.example.pharmacySystem.repository.PharmacyRepository;

@Service
public class PharmacyService {

	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	public List<PharmacyDto> findAll(){
		List<Pharmacy> allPharmacies = pharmacyRepository.findAll();
		List<PharmacyDto> myPharmacies = new ArrayList<PharmacyDto>();
		
		for(Pharmacy p : allPharmacies) 
			myPharmacies.add(new PharmacyDto(p));
		
		return myPharmacies;
	}
	
	public PharmacyDto findOneById(Long id) {
		Pharmacy pharmacy = pharmacyRepository.findOneById(id);	
		return new PharmacyDto(pharmacy);
	}
	
	public PharmacyDto findOneByName(String name) {
		Pharmacy pharmacy = pharmacyRepository.findOneByName(name);
		return new PharmacyDto(pharmacy);
	}
}
