package com.example.pharmacySystem.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.pharmacySystem.dto.PharmacyAdministratorDto;
import com.example.pharmacySystem.model.PharmacyAdministrator;
import com.example.pharmacySystem.service.PharmacyAdministratorService;

@RestController
public class PharmacyAdministratorController {

	@Autowired
	private PharmacyAdministratorService pharmacyAdminService;
	
	@GetMapping(value = "/getAllPharmacyAdmins", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PharmacyAdministratorDto>> getAllPharmacyAdmins() {
		List<PharmacyAdministrator> admins = pharmacyAdminService.findAll();
		
		if(admins == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		List<PharmacyAdministratorDto> adminsDto = new ArrayList<PharmacyAdministratorDto>();
				
		for(PharmacyAdministrator p : admins)
			adminsDto.add(new PharmacyAdministratorDto(p));
		
		return new ResponseEntity<List<PharmacyAdministratorDto>>(adminsDto, HttpStatus.OK);
	}
	
	@PostMapping(value = "/addPharmacyAdmin", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PharmacyAdministratorDto> addPharmacyAdmin(@RequestBody PharmacyAdministrator admin) throws NullPointerException{
		PharmacyAdministrator a = pharmacyAdminService.create(admin);
		
		if(a == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		PharmacyAdministratorDto adminDto = new PharmacyAdministratorDto(a);
		
		return new ResponseEntity<PharmacyAdministratorDto>(adminDto, HttpStatus.CREATED);
	}
}
