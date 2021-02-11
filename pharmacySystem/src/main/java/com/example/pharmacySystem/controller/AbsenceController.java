package com.example.pharmacySystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pharmacySystem.model.Absence;
import com.example.pharmacySystem.service.AbsenceService;

@RestController
public class AbsenceController {

	@Autowired
	private AbsenceService absenceService;
	
	@GetMapping(value = "/getPharmacistRequests/{pharmacyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Absence>> getPharmacistRequests(@PathVariable("pharmacyId") Long pharmacyId){
		List<Absence> absences = absenceService.getAllPharmacistsAbsences(pharmacyId);
		
		if(absences == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<List<Absence>>(absences, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getDermatologistRequests", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Absence>> getDermatologistRequests(){
		List<Absence> absences = absenceService.getAllDermatologistAbsences();
		
		if(absences == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<List<Absence>>(absences, HttpStatus.OK);
	}
	
	@PutMapping(value = "/declineAbsence/{absenceId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Absence> declineAbsence(@PathVariable("absenceId") Long absenceId){
		Absence a = absenceService.declineAbsence(absenceId);
		
		if(a == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<Absence>(a, HttpStatus.OK);
	}
	
	@PutMapping(value = "/acceptAbsence/{absenceId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Absence> acceptAbsence(@PathVariable("absenceId") Long absenceId){
		Absence a = absenceService.acceptAbsence(absenceId);
		
		if(a == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<Absence>(a, HttpStatus.OK);
	}
}
