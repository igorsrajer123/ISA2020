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

import com.example.pharmacySystem.dto.DermatologistDto;
import com.example.pharmacySystem.model.Dermatologist;
import com.example.pharmacySystem.service.DermatologistService;

@RestController
public class DermatologistController {

	@Autowired
	private DermatologistService dermatologistService;
	
	@GetMapping(value = "/getAllDermatologists", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DermatologistDto>> getAllDermatologists(){
		List<Dermatologist> dermatologists = dermatologistService.findAll();
		
		if(dermatologists == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		List<DermatologistDto> dermatologistsDto = new ArrayList<DermatologistDto>();
		for(Dermatologist d : dermatologists)
			dermatologistsDto.add(new DermatologistDto(d));
		
		return new ResponseEntity<List<DermatologistDto>>(dermatologistsDto, HttpStatus.OK);
	}
	
	@PostMapping(value = "/addDermatologist", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DermatologistDto> addDermatologist(@RequestBody Dermatologist dermatologist){
		Dermatologist newDermatologist = dermatologistService.Create(dermatologist);
		
		if(newDermatologist == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		DermatologistDto newDermatologistDto = new DermatologistDto(newDermatologist);
		
		return new ResponseEntity<DermatologistDto>(newDermatologistDto, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/getPharmacyDermatologists/{pharmacyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DermatologistDto>> getPharmacyDermatologists(@PathVariable("pharmacyId") Long pharmacyId){
		List<Dermatologist> myDermatologists = dermatologistService.getPharmacyDermatologists(pharmacyId);
		
		if(myDermatologists == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		List<DermatologistDto> dermatologistsDto = new ArrayList<DermatologistDto>();
		for(Dermatologist d : myDermatologists)
			dermatologistsDto.add(new DermatologistDto(d));
		
		return new ResponseEntity<List<DermatologistDto>>(dermatologistsDto, HttpStatus.OK);
	}
}
