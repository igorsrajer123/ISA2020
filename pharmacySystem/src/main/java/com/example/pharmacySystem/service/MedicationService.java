package com.example.pharmacySystem.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.pharmacySystem.model.Medication;
import com.example.pharmacySystem.repository.MedicationRepository;

@Service
public class MedicationService {

	@Autowired
	private MedicationRepository medicationRepository;
	
	public List<Medication> findAll(){
		List<Medication> allMeds = medicationRepository.findAll();
		return allMeds;
	}
	
	public Medication findOneById(Long id) {
		Medication medication = medicationRepository.findOneById(id);
		return medication;
	}
	
	public Medication findOneByName(String name) {
		Medication medication = medicationRepository.findOneByName(name);
		return medication;
	}
}
