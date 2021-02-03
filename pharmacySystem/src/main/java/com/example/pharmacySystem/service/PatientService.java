package com.example.pharmacySystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.pharmacySystem.dto.MedicationDto;
import com.example.pharmacySystem.model.Medication;
import com.example.pharmacySystem.model.Patient;
import com.example.pharmacySystem.repository.MedicationRepository;
import com.example.pharmacySystem.repository.PatientRepository;

@Service
public class PatientService {

	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private MedicationRepository medicationRepository;
	
	public List<Patient> findAll(){
		return patientRepository.findAll();
	}
	
	public Patient findOneById(Long id) {
		return patientRepository.findOneById(id);
	}
	
	public Patient findOneByUserId(Long id) {
		return patientRepository.findOneByUserId(id);
	}
	
	public Patient save(Patient patient) {
		return patientRepository.save(patient);
	}
	
	public Patient updatePatient(Patient patient) {
		Patient myPatient = patientRepository.findOneById(patient.getId());
		myPatient.setAddress(patient.getAddress());
		myPatient.setCity(patient.getCity());
		myPatient.setCountry(patient.getCountry());
		myPatient.setPhoneNumber(patient.getPhoneNumber());
		myPatient.getUser().setFirstName(patient.getUser().getFirstName());
		myPatient.getUser().setLastName(patient.getUser().getLastName());
		
		if(patient.getUser().getPassword().contains("$2a$"))
			myPatient.getUser().setPassword(patient.getUser().getPassword());
		else
			myPatient.getUser().setPassword(passwordEncoder.encode(patient.getUser().getPassword()));
		
		return myPatient;
	}
	
	public Patient addPatientAllergicMedication(Long id, MedicationDto medicationDto){
		Patient myPatient = patientRepository.findOneById(id);
		
		Medication medication = medicationRepository.findOneByName(medicationDto.getName());
		myPatient.getAllergicOn().add(medication);
		
		return patientRepository.save(myPatient);
	}
}
