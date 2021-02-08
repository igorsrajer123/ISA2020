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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.pharmacySystem.dto.CounselingDto;
import com.example.pharmacySystem.model.Counseling;
import com.example.pharmacySystem.service.CounselingService;

@RestController
public class CounselingController {

	@Autowired
	private CounselingService counselingService;
	
	@PostMapping(value = "/createCounseling", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CounselingDto> createCounseling(@RequestBody CounselingDto counselingDto){
		Counseling newCounseling = counselingService.create(counselingDto);
		
		if(newCounseling == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		CounselingDto cDto = new CounselingDto(newCounseling);
		
		return new ResponseEntity<CounselingDto>(cDto, HttpStatus.OK);
	}
	//@PreAuthorize("hasRole('ROLE_PATIENT')")
	
	@GetMapping(value = "/getPatientActiveCounselings/{patientId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CounselingDto>> getPatientActiveCounselings(@PathVariable("patientId") Long id){
		List<Counseling> counselings = counselingService.getPatientActiveCounselings(id);
		
		if(counselings == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		List<CounselingDto> counselingsDto = new ArrayList<CounselingDto>();
		for(Counseling c : counselings) 
			counselingsDto.add(new CounselingDto(c));
		
		return new ResponseEntity<List<CounselingDto>>(counselingsDto, HttpStatus.OK);
	}
	
	@PutMapping(value = "/cancelCounseling/{counselingId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CounselingDto> cancelCounseling(@PathVariable("counselingId") Long id){
		Counseling counseling = counselingService.cancelCounseling(id);
		
		if(counseling == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		CounselingDto cDto = new CounselingDto(counseling);
		
		return new ResponseEntity<CounselingDto>(cDto, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getPharmacistCounselings/{pharmacist}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CounselingDto>> getPharmacistCounselings(@PathVariable("pharmacist") Long id){
		List<Counseling> counselings = counselingService.findAllByPharmacistId(id);
		
		if(counselings == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		List<CounselingDto> counselingsDto = new ArrayList<CounselingDto>();
		for(Counseling c : counselings) 
			counselingsDto.add(new CounselingDto(c));
		
		return new ResponseEntity<List<CounselingDto>>(counselingsDto, HttpStatus.OK);
	}
}
