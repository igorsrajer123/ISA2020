package com.example.pharmacySystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pharmacySystem.model.Patient;
import com.example.pharmacySystem.repository.PatientRepository;

@Service
public class PatientService {

	@Autowired
	private PatientRepository patientRepository;
	
	public Patient findOneById(Long id) {
		return patientRepository.findOneById(id);
	}
	
	public Patient findOneByUserId(Long id) {
		return patientRepository.findOneByUserId(id);
	}
	
	public Patient save(Patient patient) {
		return patientRepository.save(patient);
	}
}
