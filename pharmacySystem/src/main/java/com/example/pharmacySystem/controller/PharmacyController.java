package com.example.pharmacySystem.controller;

import java.util.ArrayList;
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

import com.example.pharmacySystem.dto.PharmacyDto;
import com.example.pharmacySystem.model.Pharmacy;
import com.example.pharmacySystem.service.PharmacyService;

@RestController
public class PharmacyController {

	@Autowired
	private PharmacyService pharmacyService;
	
	@GetMapping(value = "/getAllPharmacies", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PharmacyDto>> getAllPharmacies(){
		List<Pharmacy> allPharmacies = pharmacyService.findAll();
		
		if(allPharmacies == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		List<PharmacyDto> myPharmacies = new ArrayList<PharmacyDto>();
		
		for(Pharmacy p : allPharmacies)
			myPharmacies.add(new PharmacyDto(p));
		
		return new ResponseEntity<List<PharmacyDto>>(myPharmacies, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getPharmacy/{pharmacyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PharmacyDto> getPharmacyById(@PathVariable("pharmacyId") Long id){
		Pharmacy pharmacy = pharmacyService.findOneById(id);
		
		if(pharmacy == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<PharmacyDto>(new PharmacyDto(pharmacy), HttpStatus.OK);
	}
	
	@PostMapping(value = "/addPharmacy", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PharmacyDto> addPharmacy(@RequestBody PharmacyDto pharmacy){
		Pharmacy myPharmacy = pharmacyService.create(pharmacy);
		
		if(myPharmacy == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<PharmacyDto>(pharmacy, HttpStatus.OK);
	}
}
