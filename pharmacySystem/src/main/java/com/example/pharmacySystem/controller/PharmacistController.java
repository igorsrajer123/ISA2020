package com.example.pharmacySystem.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.example.pharmacySystem.dto.PharmacistDto;
import com.example.pharmacySystem.model.Pharmacist;
import com.example.pharmacySystem.service.PharmacistService;

@RestController
public class PharmacistController {

	@Autowired
	private PharmacistService pharmacistService;
	
	@GetMapping(value = "/getAllPharmacists", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PharmacistDto>> getAllPharmacists(){
		List<Pharmacist> pharmacists = pharmacistService.findAll();
		
		if(pharmacists == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		List<PharmacistDto> pharmacistsDto = new ArrayList<PharmacistDto>();
		for(Pharmacist p : pharmacists)
			pharmacistsDto.add(new PharmacistDto(p));
		
		return new ResponseEntity<List<PharmacistDto>>(pharmacistsDto, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getPharmacyPharmacists/{pharmacyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PharmacistDto>> getPharmacyPharmacists(@PathVariable("pharmacyId") Long pharmacyId){
		List<Pharmacist> pharmacists = pharmacistService.getPharmacyPharmacists(pharmacyId);
		
		if(pharmacists == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		List<PharmacistDto> pharmacistsDto = new ArrayList<PharmacistDto>();
		for(Pharmacist p : pharmacists)
			pharmacistsDto.add(new PharmacistDto(p));
		
		return new ResponseEntity<List<PharmacistDto>>(pharmacistsDto, HttpStatus.OK);
	}
}
