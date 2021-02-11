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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.pharmacySystem.dto.MedicationsPharmaciesDto;
import com.example.pharmacySystem.model.MedicationsPharmacies;
import com.example.pharmacySystem.service.MedicationsPharmaciesService;

@RestController
public class MedicationsPharmaciesController {

	@Autowired
	private MedicationsPharmaciesService mpService;
	
	@GetMapping(value = "/getAllMedicationsInPharmacies", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MedicationsPharmaciesDto>> getAllMedicationsInPharmacies(){
		List<MedicationsPharmacies> medsPharmacies = mpService.findAllByDeleted(false);
		
		List<MedicationsPharmaciesDto> asa = new ArrayList<MedicationsPharmaciesDto>();
		
		for(MedicationsPharmacies m : medsPharmacies)
			asa.add(new MedicationsPharmaciesDto(m));
		
		return new ResponseEntity<List<MedicationsPharmaciesDto>>(asa, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getMedInPharmacyById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MedicationsPharmaciesDto> getMedInPharmacyById(@PathVariable("id") Long id){
		MedicationsPharmacies mp = mpService.findOneById(id);
		
		MedicationsPharmaciesDto mpDto = new MedicationsPharmaciesDto(mp);
		
		return new ResponseEntity<MedicationsPharmaciesDto>(mpDto, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/findAllMedsByPharmacyId/{pharmacyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MedicationsPharmaciesDto>> getMedicationsByPharmacyId(@PathVariable("pharmacyId") Long id){
		List<MedicationsPharmacies> mp = mpService.findAllByPharmacyId(id);
		
		List<MedicationsPharmaciesDto> mpDto = new ArrayList<MedicationsPharmaciesDto>();
		
		for(MedicationsPharmacies m : mp)
			mpDto.add(new MedicationsPharmaciesDto(m));
		
		return new ResponseEntity<List<MedicationsPharmaciesDto>>(mpDto, HttpStatus.OK);
	}
	
	@GetMapping(value = "/findOneByPharmacyIdAndMedicationId/{pharmacyId}/{medicationId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MedicationsPharmaciesDto> findOneByPharmacyIdAndMedicationId(@PathVariable("pharmacyId") Long pharmacyId, @PathVariable("medicationId") Long medicationId){
		MedicationsPharmacies mp = mpService.findOneByPharmacyIdAndMedicationIdAndDeleted(pharmacyId, medicationId, false);
		
		MedicationsPharmaciesDto mpDto = new MedicationsPharmaciesDto(mp);
		
		return new ResponseEntity<MedicationsPharmaciesDto>(mpDto, HttpStatus.OK);
	}
	
	@PutMapping(value = "/updateMedicationInPharmacy", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_PHARMACY_ADMIN')")
	public ResponseEntity<MedicationsPharmaciesDto> updateMedicationInPharmacy(@RequestBody MedicationsPharmaciesDto dto){
		MedicationsPharmacies mp = mpService.updateMedicationInPharmacy(dto);
		
		MedicationsPharmaciesDto mpDto = new MedicationsPharmaciesDto(mp);
		
		return new ResponseEntity<MedicationsPharmaciesDto>(mpDto, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getMedicationInPharmacyFromReservation/{reservationId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MedicationsPharmaciesDto> getMedicationInPharmacyFromReservation(@PathVariable("reservationId") Long id){
		MedicationsPharmacies mp = mpService.getMedicationFromReservation(id);
		
		MedicationsPharmaciesDto mpDto = new MedicationsPharmaciesDto(mp);
		
		return new ResponseEntity<MedicationsPharmaciesDto>(mpDto, HttpStatus.OK);
	}
	
}
