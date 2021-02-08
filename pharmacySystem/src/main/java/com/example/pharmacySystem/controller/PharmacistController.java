package com.example.pharmacySystem.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.pharmacySystem.dto.PharmacistDto;
import com.example.pharmacySystem.model.Pharmacist;
import com.example.pharmacySystem.service.PharmacistService;

@RestController
public class PharmacistController {

	@Autowired
	private PharmacistService pharmacistService;
	
	@GetMapping(value = "/getAllPharmacists", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Pharmacist>> getAllPharmacists(){
		List<Pharmacist> pharmacists = pharmacistService.findAll();
		
		if(pharmacists == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		List<PharmacistDto> pharmacistsDto = new ArrayList<PharmacistDto>();
		for(Pharmacist p : pharmacists)
			pharmacistsDto.add(new PharmacistDto(p));
		
		return new ResponseEntity<List<Pharmacist>>(pharmacists, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getPharmacyPharmacists/{pharmacyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PharmacistDto>> getPharmacyPharmacists(@PathVariable("pharmacyId") Long pharmacyId){
		List<Pharmacist> pharmacists = pharmacistService.getPharmacyActivePharmacists(pharmacyId);
		
		if(pharmacists == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		List<PharmacistDto> pharmacistsDto = new ArrayList<PharmacistDto>();
		for(Pharmacist p : pharmacists)
			pharmacistsDto.add(new PharmacistDto(p));
		
		return new ResponseEntity<List<PharmacistDto>>(pharmacistsDto, HttpStatus.OK);
	}
	
	@PostMapping(value = "/addPharmacist", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_PHARMACY_ADMIN')")
	public ResponseEntity<PharmacistDto> addPharmacist(@RequestBody Pharmacist pharmacist){
		Pharmacist newPharmacist = pharmacistService.create(pharmacist);
		
		if(newPharmacist == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		PharmacistDto pharmacistDto = new PharmacistDto(newPharmacist);
		
		return new ResponseEntity<PharmacistDto>(pharmacistDto, HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/removePharmacistFromPharmacy/{pharmacistId}/{pharmacyId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_PHARMACY_ADMIN')")
	public ResponseEntity<PharmacistDto> removePharmacistFromPharmacy(@PathVariable("pharmacistId") Long pharmacistId, @PathVariable("pharmacyId") Long pharmacyId){
		Pharmacist pharmacist = pharmacistService.removePharmacistFromPharmacy(pharmacistId, pharmacyId);
		
		if(pharmacist == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		PharmacistDto pharmacistDto = new PharmacistDto(pharmacist);
		
		return new ResponseEntity<PharmacistDto>(pharmacistDto, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getAvailablePharmacists/{pharmacyId}/{time}/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PharmacistDto>> getAvailablePharmacists(@PathVariable("pharmacyId") Long pharmacyId, @PathVariable("time") String time, @PathVariable("date") String date){
		List<Pharmacist> pharmacists = pharmacistService.getAvailablePharmacists(pharmacyId, time, date);
		
		if(pharmacists == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		List<PharmacistDto> pharmacistsDto = new ArrayList<PharmacistDto>();
		for(Pharmacist p : pharmacists)
			pharmacistsDto.add(new PharmacistDto(p));
		
		return new ResponseEntity<List<PharmacistDto>>(pharmacistsDto, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getPharmacistFromCounseling/{counselingId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PharmacistDto> getPharmacistFromCounseling(@PathVariable("counselingId") Long id){
		Pharmacist p = pharmacistService.getPharmacistFromCounseling(id);
		
		if(p == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		PharmacistDto pDto = new PharmacistDto(p);
		
		return new ResponseEntity<PharmacistDto>(pDto, HttpStatus.OK);
	}
}
