package com.example.pharmacySystem.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pharmacySystem.dto.MedicationTypeDto;
import com.example.pharmacySystem.model.MedicationType;
import com.example.pharmacySystem.service.MedicationTypeService;

@RestController
public class MedicationTypeController {

	@Autowired
	private MedicationTypeService typeService;
	
	@GetMapping(value = "/getAllMedicationTypes", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MedicationTypeDto>> getAllTypes(){
		List<MedicationType> types = typeService.findAll();
		
		if(types == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		List<MedicationTypeDto> allTypes = new ArrayList<MedicationTypeDto>();
		
		for(MedicationType m : types)
			allTypes.add(new MedicationTypeDto(m));
		
		return new ResponseEntity<List<MedicationTypeDto>>(allTypes, HttpStatus.OK);
	}
}
