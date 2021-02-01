package com.example.pharmacySystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.pharmacySystem.dto.PharmacyDto;
import com.example.pharmacySystem.service.PharmacyService;

@RestController
public class PharmacyController {

	@Autowired
	private PharmacyService pharmacyService;
	
	@GetMapping(value = "/getAllPharmacies", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PharmacyDto>> getAllPharmacies(){
		List<PharmacyDto> allPharmacies = pharmacyService.findAll();
		
		if(allPharmacies == null) return new ResponseEntity<List<PharmacyDto>>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<List<PharmacyDto>>(allPharmacies, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getPharmacy/{pharmacyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PharmacyDto> getPharmacyById(@PathVariable("pharmacyId") Long id){
		PharmacyDto pharmacy = pharmacyService.findOneById(id);
		
		if(pharmacy == null) return new ResponseEntity<PharmacyDto>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<PharmacyDto>(pharmacy, HttpStatus.OK);
	}
}
