package com.example.pharmacySystem.controller;

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
import com.example.pharmacySystem.model.Patient;
import com.example.pharmacySystem.service.PatientService;

@RestController
public class PatientController {

	@Autowired
	private PatientService patientService;
	
	@PostMapping(value = "/updatePatient", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<Patient> updatePatient(@RequestBody Patient patient) {
		Patient myPatient = patientService.updatePatient(patient);
		patientService.save(myPatient);
		
		if(myPatient == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<Patient>(myPatient, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getAllPatients", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Patient>> getAllPatients() {
		List<Patient> all = patientService.findAll();
		
		return new ResponseEntity<List<Patient>>(all, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getPatientById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Patient> getById(@PathVariable("id") Long id) {
		Patient p = patientService.findOneById(id);
		
		return new ResponseEntity<Patient>(p, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getPatientByUserId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Patient> getPatientByUserId(@PathVariable("id") Long id){
		Patient patient = patientService.findOneByUserId(id);
		
		if(patient == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<Patient>(patient, HttpStatus.OK);		
	}
}
