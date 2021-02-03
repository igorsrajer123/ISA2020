package com.example.pharmacySystem.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.pharmacySystem.dto.MedicationDto;
import com.example.pharmacySystem.model.Medication;
import com.example.pharmacySystem.model.MedicationType;
import com.example.pharmacySystem.repository.MedicationRepository;
import com.example.pharmacySystem.repository.MedicationTypeRepository;

@Service
public class MedicationService {

	@Autowired
	private MedicationRepository medicationRepository;
	
	@Autowired
	private MedicationTypeRepository typeRepository;
	
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
	
	public Medication create(MedicationDto medication) {
		Medication newMed = new Medication();
		newMed.setName(medication.getName());
		newMed.setChemicalComposition(medication.getChemicalComposition());
		newMed.setCode(medication.getCode());
		newMed.setDailyIntake(medication.getDailyIntake());
		newMed.setSubstitution(medication.getSubstitution());
		newMed.setSideEffects(medication.getSideEffects());
		MedicationType type = typeRepository.findOneByName(medication.getMedicationType().getName());
		newMed.setMedicationType(type);
		
		return medicationRepository.save(newMed);
	}
}
