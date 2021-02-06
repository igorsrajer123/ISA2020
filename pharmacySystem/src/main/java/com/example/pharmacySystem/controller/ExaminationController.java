package com.example.pharmacySystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.pharmacySystem.model.Examination;
import com.example.pharmacySystem.service.ExaminationService;

@RestController
public class ExaminationController {

	@Autowired
	private ExaminationService examinationService;
	
	@GetMapping(value = "/getAllExaminations", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Examination>> getAllExaminations(){
		List<Examination> ex = examinationService.findAll();
		
		return new ResponseEntity<List<Examination>>(ex, HttpStatus.OK);
	}
	
	@PostMapping(value = "/createExamination", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Examination> createExamination(@RequestBody Examination e){
		Examination ex = examinationService.createExamination(e);
		
		if(ex == null) return new ResponseEntity<Examination>(HttpStatus.NOT_FOUND);
		
		if(ex.getId() == -1) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		
		return new ResponseEntity<Examination>(ex, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getAllDermatologistExaminations/{dermaId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Examination>> getAllDermatologistExaminations(@PathVariable("dermaId") Long id){
		List<Examination> examinations = examinationService.findAllByDermatologistId(id);
		
		return new ResponseEntity<List<Examination>>(examinations, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getDermatologistPharmacyExaminations/{dermatologistId}/{pharmacyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Examination>> getDermatologistPharmacyExaminations(@PathVariable("dermatologistId") Long dermaId, @PathVariable("pharmacyId") Long pharmaId){
		List<Examination> examinations = examinationService.findAllByDermatologistIdAndPharmacyId(dermaId, pharmaId);
		
		return new ResponseEntity<List<Examination>>(examinations, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getDermatologistPharmacyExaminationsByStatus/{status}/{dermatologistId}/{pharmacyId}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Examination>> getDermatologistPharmacyExaminationsByStatus(@PathVariable("status") String status, @PathVariable("dermatologistId") Long dermaId, @PathVariable("pharmacyId") Long pharmaId){
		List<Examination> examinations = examinationService.findAllByStatusAndDermatologistIdAndPharmacyId(status, dermaId, pharmaId);
		
		return new ResponseEntity<List<Examination>>(examinations, HttpStatus.OK);
	}
}
