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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.pharmacySystem.dto.MedicationDto;
import com.example.pharmacySystem.model.Medication;
import com.example.pharmacySystem.model.Patient;
import com.example.pharmacySystem.model.Pharmacy;
import com.example.pharmacySystem.service.MedicationService;
import com.example.pharmacySystem.service.PatientService;
import com.example.pharmacySystem.service.PharmacyService;

@RestController
public class MedicationController {

	@Autowired
	private MedicationService medicationService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private PharmacyService pharmacyService;
	
	@GetMapping(value = "/getAllMedications", produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<MedicationDto>> getAllMedications(){
		List<Medication> allMedications = medicationService.findAll();
		
		if(allMedications == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		List<MedicationDto> allMedicationsDto = new ArrayList<MedicationDto>();
		
		for(Medication m : allMedications)
			allMedicationsDto.add(new MedicationDto(m));
		
		return new ResponseEntity<List<MedicationDto>>(allMedicationsDto, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getPatientAllergicMedications/{patientId}", produces = MediaType.APPLICATION_JSON_VALUE)
//	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<MedicationDto>> getPatientAllergicMedications(@PathVariable("patientId") Long id){
		Patient myPatient = patientService.findOneById(id);
		
		List<Medication> patientMeds = myPatient.getAllergicOn();
		List<MedicationDto> patientMedsDto = new ArrayList<MedicationDto>();
		
		for(Medication m : patientMeds) 
			patientMedsDto.add(new MedicationDto(m));
		
		return new ResponseEntity<List<MedicationDto>>(patientMedsDto, HttpStatus.OK);
	}
	
	@PostMapping(value = "/addPatientAllergicMedication/{patientId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<MedicationDto>> addPatientAllergicMedication(@PathVariable("patientId") Long id, @RequestBody MedicationDto medication){
		Patient myPatient = patientService.addPatientAllergicMedication(id, medication);
		
		if(myPatient == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		List<Medication> patientMeds = myPatient.getAllergicOn();
		List<MedicationDto> patientMedsDto = new ArrayList<MedicationDto>();
		
		for(Medication m : patientMeds)
			patientMedsDto.add(new MedicationDto(m));
		
		return new ResponseEntity<List<MedicationDto>>(patientMedsDto ,HttpStatus.OK);
	}
	
	@PostMapping(value = "/removePatientAllergicMedication/{patientId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<MedicationDto>> removePatientAllergicMedication(@PathVariable("patientId") Long id, @RequestBody MedicationDto medicationDto){
		Patient myPatient = patientService.removePatientAllergicMedication(id, medicationDto);
		
		if(myPatient == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
					
		List<MedicationDto> patientMedsDto = new ArrayList<MedicationDto>();
		
		for(Medication m : myPatient.getAllergicOn())
			patientMedsDto.add(new MedicationDto(m));
		
		return new ResponseEntity<List<MedicationDto>>(patientMedsDto ,HttpStatus.OK);
	}
	
	@GetMapping(value = "/getPharmacyMedications/{pharmacyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MedicationDto>> getPharmacyMedications(@PathVariable("pharmacyId") Long id){
		Pharmacy pharmacy = pharmacyService.findOneById(id);
		
		if(pharmacy == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		List<Medication> pharmacyMeds = pharmacy.getMedications();
		List<MedicationDto> pharmacyMedsDto = new ArrayList<MedicationDto>();
		
		if(pharmacyMeds == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		for(Medication m : pharmacyMeds)
			pharmacyMedsDto.add(new MedicationDto(m));
		
		return new ResponseEntity<List<MedicationDto>>(pharmacyMedsDto, HttpStatus.OK);
	}
	
	@PostMapping(value = "/addMedication", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MedicationDto> addMed(@RequestBody MedicationDto medication){
		Medication newMed = medicationService.create(medication);
		
		if(newMed == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		MedicationDto medDto = new MedicationDto(newMed);
		
		return new ResponseEntity<MedicationDto>(medDto, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/getMedicationsNotInPharmacy/{pharmacyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MedicationDto>> getMedicationsNotInPharmacy(@PathVariable("pharmacyId") Long id){
		List<Medication> meds = medicationService.medicationsNotInPharmacy(id);
		
		if(meds == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		List<MedicationDto> myMeds = new ArrayList<MedicationDto>();
		for(Medication m : meds)
			myMeds.add(new MedicationDto(m));
		
		return new ResponseEntity<List<MedicationDto>>(myMeds, HttpStatus.OK);
	}
	
	@PostMapping(value = "/addMedicationToPharmacy/{pharmacyId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MedicationDto> addMedicationToPharmacy(@PathVariable("pharmacyId") Long id, @RequestBody MedicationDto medicationDto){
		Medication med = medicationService.addMedicationToPharmacy(id, medicationDto);
		
		if(med == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		MedicationDto medDto = new MedicationDto(med);
		return new ResponseEntity<MedicationDto>(medDto, HttpStatus.OK);
	}
	
	@GetMapping(value = "/findMedicationsByName/{medName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Medication>> getMedicationsNotInPharmacy(@PathVariable("medName") String name){
		List<Medication> meds = medicationService.findAllByName(name);
		
		if(meds == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<List<Medication>>(meds, HttpStatus.OK);
	}	
	
	@GetMapping(value = "/getMedicationById/{medId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MedicationDto> getMedicationById(@PathVariable("medId") Long id){
		Medication myMed = medicationService.findOneById(id);

		if(myMed == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		MedicationDto medDto = new MedicationDto(myMed);
		return new ResponseEntity<MedicationDto>(medDto, HttpStatus.OK);
	}
	
	@PostMapping(value = "/removeMedicationFromPharmacy/{pharmacyId}/{medId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MedicationDto> removeMedicationFromPharmacy(@PathVariable("pharmacyId") Long id, @PathVariable("medId") Long medId){
		Medication myMed = medicationService.removeMedicationFromPharmacy(id, medId);
		
		if(myMed == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		MedicationDto myMedDto = new MedicationDto(myMed);
		
		return new ResponseEntity<MedicationDto>(myMedDto, HttpStatus.OK);
	}
	
	@PutMapping(value = "/updateMedication", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MedicationDto> updateMedication(@RequestBody MedicationDto medDto){
		Medication med = medicationService.updateMedication(medDto);
		
		if(med == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		MedicationDto myMedDto = new MedicationDto(med);
		
		return new ResponseEntity<MedicationDto>(myMedDto, HttpStatus.OK);
	}
}
