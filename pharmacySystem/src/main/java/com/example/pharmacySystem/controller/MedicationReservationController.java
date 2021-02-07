package com.example.pharmacySystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.pharmacySystem.model.MedicationReservation;
import com.example.pharmacySystem.service.MedicationReservationService;

@RestController
public class MedicationReservationController {

	@Autowired
	private MedicationReservationService medicationReservationService;
	
	@PostMapping(value = "/createMedicationReservation", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MedicationReservation> createMedicationReservation(@RequestBody MedicationReservation mr){
		MedicationReservation medReservation = medicationReservationService.create(mr);
		
		if(medReservation == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<MedicationReservation>(medReservation, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getPatientMedicationReservations/{patientId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MedicationReservation>> getPatientReservations(@PathVariable("patientId") Long id){
		List<MedicationReservation> patientReservations = medicationReservationService.getPatientReservations(id);
		
		return new ResponseEntity<List<MedicationReservation>>(patientReservations, HttpStatus.OK);
	}
	
	@GetMapping(value = "/findPatientActiveMedicationReservations/{patientId}/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MedicationReservation>> getAllByPatientIdAndStatus(@PathVariable("patientId") Long id, @PathVariable("status") String status){
		List<MedicationReservation> medReservations = medicationReservationService.findAllByPatientIdAndStatus(id, status);
			
		return new ResponseEntity<List<MedicationReservation>>(medReservations, HttpStatus.OK);
	}
	
	@PutMapping(value = "/cancelMedicationReservation/{patientId}/{reservationId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MedicationReservation> cancelMedicationReservation(@PathVariable("patientId") Long patientId, @PathVariable("reservationId") Long reservationId){
		MedicationReservation medReservations = medicationReservationService.cancelReservation(patientId, reservationId);
		
		if(medReservations == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<MedicationReservation>(medReservations, HttpStatus.OK);
	}
}
