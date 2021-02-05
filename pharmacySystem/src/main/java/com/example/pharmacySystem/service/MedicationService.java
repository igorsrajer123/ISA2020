package com.example.pharmacySystem.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.pharmacySystem.dto.MedicationDto;
import com.example.pharmacySystem.dto.MedicationsPharmaciesDto;
import com.example.pharmacySystem.model.Medication;
import com.example.pharmacySystem.model.MedicationType;
import com.example.pharmacySystem.model.MedicationsPharmacies;
import com.example.pharmacySystem.model.Pharmacy;
import com.example.pharmacySystem.repository.MedicationRepository;
import com.example.pharmacySystem.repository.MedicationTypeRepository;
import com.example.pharmacySystem.repository.MedicationsPharmaciesRepository;
import com.example.pharmacySystem.repository.PharmacyRepository;

@Service
public class MedicationService {

	@Autowired
	private MedicationRepository medicationRepository;
	
	@Autowired
	private MedicationTypeRepository typeRepository;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private MedicationsPharmaciesRepository medicationsPharmaciesRepository;
	
	public List<Medication> findAll(){
		List<Medication> allMeds = medicationRepository.findAll();
		return allMeds;
	}
	
	public Medication findOneById(Long id) {
		Medication medication = medicationRepository.findOneById(id);
		return medication;
	}
	
	public Medication findOneByName(String name){
		return medicationRepository.findOneByName(name);
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
	
	public List<Medication> getPharmacyMedications(Long pharmacyId){
		List<MedicationsPharmacies> allMedsInPharmacy = medicationsPharmaciesRepository.findAllByPharmacyId(pharmacyId);
		List<Medication> ourMedications = new ArrayList<Medication>();
		
		for(MedicationsPharmacies m : allMedsInPharmacy)
			if(!m.isDeleted())
				ourMedications.add(medicationRepository.findOneById(m.getMedication().getId()));
		
		return ourMedications;
	}
	
	public List<Medication> medicationsNotInPharmacy(Long pharmacyId){
		List<Medication> pharmacyMeds = getPharmacyMedications(pharmacyId);
		List<Medication> allMeds = medicationRepository.findAll();
		allMeds.removeAll(pharmacyMeds);
		
		return allMeds;
	}
	
	public Medication addMedicationToPharmacy(MedicationsPharmaciesDto medPharmacies) {
		Pharmacy myPharmacy = pharmacyRepository.findOneById(medPharmacies.getPharmacy().getId());
		Medication myMedication = medicationRepository.findOneById(medPharmacies.getMedication().getId());
		List<MedicationsPharmacies> allMedsInPharmacies = medicationsPharmaciesRepository.findAll();
				
		MedicationsPharmacies newMedInPharmacy = new MedicationsPharmacies();
		newMedInPharmacy.setPrice(medPharmacies.getPrice());
		newMedInPharmacy.setAmount(medPharmacies.getAmount());
		newMedInPharmacy.setMedication(myMedication);
		newMedInPharmacy.setPharmacy(myPharmacy);
		newMedInPharmacy.setDeleted(false);
		allMedsInPharmacies.add(newMedInPharmacy);
		myPharmacy.getMedicationsInPharmacy().add(newMedInPharmacy);
		myMedication.getMedicationsInPharmacy().add(newMedInPharmacy);
		medicationRepository.save(myMedication);
		return myMedication;
	}
	
	public Medication removeMedicationFromPharmacy(Long pharmacyId, Long medicationId) {
		Pharmacy myPharmacy = pharmacyRepository.findOneById(pharmacyId);
		Medication myMedication = medicationRepository.findOneById(medicationId);

		MedicationsPharmacies mp = medicationsPharmaciesRepository.findOneByPharmacyIdAndMedicationIdAndDeleted(pharmacyId, medicationId, false);
		mp.setDeleted(true);
		myPharmacy.getMedicationsInPharmacy().remove(mp);
		myMedication.getMedicationsInPharmacy().remove(mp);
		List<MedicationsPharmacies> allMp = medicationsPharmaciesRepository.findAll();
		allMp.remove(mp);
		medicationRepository.save(myMedication);
		return myMedication;
	}
	
	public Medication updateMedication(MedicationDto medDto) {
		Medication myMedication = medicationRepository.findOneById(medDto.getId());
		
		myMedication.setName(medDto.getName());
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
