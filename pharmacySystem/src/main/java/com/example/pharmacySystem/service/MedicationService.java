package com.example.pharmacySystem.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.pharmacySystem.dto.MedicationDto;
import com.example.pharmacySystem.model.Medication;
import com.example.pharmacySystem.model.MedicationType;
import com.example.pharmacySystem.model.Pharmacy;
import com.example.pharmacySystem.repository.MedicationRepository;
import com.example.pharmacySystem.repository.MedicationTypeRepository;
import com.example.pharmacySystem.repository.PharmacyRepository;

@Service
public class MedicationService {

	@Autowired
	private MedicationRepository medicationRepository;
	
	@Autowired
	private MedicationTypeRepository typeRepository;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	public List<Medication> findAll(){
		List<Medication> allMeds = medicationRepository.findAll();
		return allMeds;
	}
	
	public Medication findOneById(Long id) {
		Medication medication = medicationRepository.findOneById(id);
		return medication;
	}
	
	public List<Medication> findAllByName(String name){
		return medicationRepository.findAllByName(name);
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
	
	public List<Medication> medicationsNotInPharmacy(Long pharmacyId){
		Pharmacy myPharmacy = pharmacyRepository.findOneById(pharmacyId);
		List<Medication> pharmacyMeds = myPharmacy.getMedications();
		List<Medication> allMeds = medicationRepository.findAll();
		List<Medication> retVal = new ArrayList<Medication>();
		List<Medication> helper = new ArrayList<Medication>();
		
		retVal = allMeds;
		retVal.removeAll(pharmacyMeds);
		
		//remove medications with same name 
		for(Medication m2 : pharmacyMeds)
			for(Medication m : retVal)
				if(m.getName().toLowerCase().equals(m2.getName().toLowerCase()))
					helper.add(m);	
							
		retVal.removeAll(helper);		
		return retVal;
	}
	
	public Medication addMedicationToPharmacy(Long pharmacyId, MedicationDto medDto) {
		Pharmacy myPharmacy = pharmacyRepository.findOneById(pharmacyId);
		Medication myMedication = medicationRepository.findOneById(medDto.getId());
		
		Medication newMedication = new Medication();
		newMedication.setName(myMedication.getName());
		newMedication.setChemicalComposition(myMedication.getChemicalComposition());
		newMedication.setCode(myMedication.getCode());
		newMedication.setDailyIntake(myMedication.getDailyIntake());
		newMedication.setMedicationType(myMedication.getMedicationType());
		newMedication.setSideEffects(myMedication.getSideEffects());
		newMedication.setSubstitution(myMedication.getSubstitution());
		newMedication.setPrice(medDto.getPrice());
		myPharmacy.getMedications().add(newMedication);
		
		medicationRepository.save(newMedication);
		return myMedication;
	}
	
	public Medication removeMedicationFromPharmacy(Long pharmacyId, Long medicationId) {
		Pharmacy myPharmacy = pharmacyRepository.findOneById(pharmacyId);
		Medication myMedication = medicationRepository.findOneById(medicationId);

		myPharmacy.getMedications().remove(myMedication);
		medicationRepository.save(myMedication);
		return myMedication;
	}
	
	public Medication updateMedication(MedicationDto medDto) {
		Medication myMedication = medicationRepository.findOneById(medDto.getId());
		
		myMedication.setName(medDto.getName());
		myMedication.setPrice(medDto.getPrice());
		myMedication.setChemicalComposition(medDto.getChemicalComposition());
		myMedication.setCode(medDto.getCode());
		myMedication.setDailyIntake(medDto.getDailyIntake());
		MedicationType type = typeRepository.findOneById(medDto.getMedicationType().getId());
		myMedication.setMedicationType(type);
		myMedication.setSideEffects(medDto.getSideEffects());
		myMedication.setSubstitution(medDto.getSubstitution());
		medicationRepository.save(myMedication);
		
		return myMedication;
	}
}
