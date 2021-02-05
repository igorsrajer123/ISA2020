package com.example.pharmacySystem.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.pharmacySystem.dto.DermatologistPharmacyHoursDto;
import com.example.pharmacySystem.model.DermatologistPharmacyHours;
import com.example.pharmacySystem.service.DermatologistPharmacyHoursService;

@RestController
public class DermatologistPharmacyHoursController {

	@Autowired
	private DermatologistPharmacyHoursService dermatologistHoursService;
	
	@GetMapping(value = "/getAllDermatologistWorkingHours",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DermatologistPharmacyHoursDto>> getAllDermatologistWorkingHours(){
		List<DermatologistPharmacyHours> hours = dermatologistHoursService.findAll();
		
		if(hours == null)  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		List<DermatologistPharmacyHoursDto> dtos = new ArrayList<DermatologistPharmacyHoursDto>();
		for(DermatologistPharmacyHours d : hours)
			dtos.add(new DermatologistPharmacyHoursDto(d));
		
		return new ResponseEntity<List<DermatologistPharmacyHoursDto>>(dtos, HttpStatus.OK);
	}
	
	@PostMapping(value = "/addDermatologistWorkingHours", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DermatologistPharmacyHoursDto> addDermatologistWorkingHours(@RequestBody DermatologistPharmacyHoursDto newHours){
		DermatologistPharmacyHours hours = dermatologistHoursService.addDermatologistWorkingHours(newHours);
		
		if(hours == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		DermatologistPharmacyHoursDto dto = new DermatologistPharmacyHoursDto(hours);
		
		return new ResponseEntity<DermatologistPharmacyHoursDto>(dto, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getOneDermatologistActiveWorkingHours/{dermatologistId}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DermatologistPharmacyHoursDto>> getDermatologistActiveWorkingHours(@PathVariable("dermatologistId") Long id){
		List<DermatologistPharmacyHours> hours = dermatologistHoursService.getDermatologistActiveWorkingHours(id);
		
		if(hours == null)  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	
		List<DermatologistPharmacyHoursDto> dtos = new ArrayList<DermatologistPharmacyHoursDto>();
		for(DermatologistPharmacyHours d : hours)
			dtos.add(new DermatologistPharmacyHoursDto(d));
		
		return new ResponseEntity<List<DermatologistPharmacyHoursDto>>(dtos, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/removeDermatologistPharmacyWorkingHours/{dermatologistId}/{pharmacyId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DermatologistPharmacyHoursDto> removeDermatologistPharmacyWorkingHours(@PathVariable("dermatologistId") Long dermatologistId, @PathVariable("pharmacyId") Long pharmacyId){
		DermatologistPharmacyHours a = dermatologistHoursService.removeDermatologistWorkingHoursFromPharmacy(dermatologistId, pharmacyId);
		
		if(a == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		DermatologistPharmacyHoursDto b = new DermatologistPharmacyHoursDto(a);
		
		return new ResponseEntity<DermatologistPharmacyHoursDto>(b, HttpStatus.OK);
	}
}
