package com.example.pharmacySystem.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.pharmacySystem.dto.MedicationDto;
import com.example.pharmacySystem.model.Medication;
import com.example.pharmacySystem.model.Patient;
import com.example.pharmacySystem.service.MedicationService;
import com.example.pharmacySystem.service.PatientService;

@RestController
public class MedicationController {

	@Autowired
	private MedicationService medicationService;
	
	@Autowired
	private PatientService patientService;
	
	@GetMapping(value = "/getAllMedications", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<MedicationDto>> getAllMedications(){
		List<MedicationDto> allMedications = medicationService.findAll();
		
		if(allMedications == null) return new ResponseEntity<List<MedicationDto>>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<List<MedicationDto>>(allMedications, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getPatientAllergicMedications/{patientId}", produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasRole('ROLE_PATIENT'")
	public ResponseEntity<List<MedicationDto>> getPatientAllergicMedications(@PathVariable("patientId") Long id){
		Patient myPatient = patientService.findOneById(id);
		
		List<Medication> patientMeds = myPatient.getAllergicOn();
		List<MedicationDto> patientMedsDto = new ArrayList<MedicationDto>();
		
		for(Medication m : patientMeds) {
			patientMedsDto.add(new MedicationDto(m));
		}
		
		return new ResponseEntity<List<MedicationDto>>(patientMedsDto, HttpStatus.OK);
	}
	
	@PostMapping(value = "/addPatientAllergicMedication/{patientId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MedicationDto>> addPatientAllergicMedication(@PathVariable("patientId") Long id, @RequestBody Medication medication){
		Patient myPatient = patientService.findOneById(id);
		
		if(medicationService.findOneByName(medication.getName()) == null)
				return new ResponseEntity<List<MedicationDto>>(HttpStatus.NOT_FOUND);
		
		List<Medication> patientMeds = myPatient.getAllergicOn();
		List<MedicationDto> patientMedsDto = new ArrayList<MedicationDto>();
		
		patientMeds.add(medication);
		patientService.save(myPatient);
		
		for(Medication m : patientMeds)
			patientMedsDto.add(new MedicationDto(m));
		
		return new ResponseEntity<List<MedicationDto>>(patientMedsDto ,HttpStatus.OK);
	}
}
