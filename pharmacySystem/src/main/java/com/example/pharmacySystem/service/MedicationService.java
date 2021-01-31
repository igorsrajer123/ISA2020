package com.example.pharmacySystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pharmacySystem.dto.MedicationDto;
import com.example.pharmacySystem.model.Medication;
import com.example.pharmacySystem.repository.MedicationRepository;

@Service
public class MedicationService {

	@Autowired
	private MedicationRepository medicationRepository;
	
	public List<MedicationDto> findAll(){
		List<Medication> allMeds = medicationRepository.findAll();
		List<MedicationDto> myMeds = new ArrayList<MedicationDto>();
		
		for(Medication m : allMeds) 
			myMeds.add(new MedicationDto(m));
		
		return myMeds;
	}
	
	public MedicationDto findOneById(Long id) {
		Medication medication = medicationRepository.findOneById(id);
		return new MedicationDto(medication);
	}
	
	public MedicationDto findOneByName(String name) {
		Medication medication = medicationRepository.findOneByName(name);
		return new MedicationDto(medication);
	}
}
