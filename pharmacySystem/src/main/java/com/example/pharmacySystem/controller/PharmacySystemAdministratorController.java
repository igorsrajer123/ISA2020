package com.example.pharmacySystem.controller;

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

import com.example.pharmacySystem.dto.PharmacyAdministratorDto;
import com.example.pharmacySystem.dto.PharmacySystemAdministratorDto;
import com.example.pharmacySystem.model.PharmacyAdministrator;
import com.example.pharmacySystem.model.PharmacySystemAdministrator;
import com.example.pharmacySystem.service.PharmacySystemAdministratorService;

@RestController
public class PharmacySystemAdministratorController {

	@Autowired
	private PharmacySystemAdministratorService systemAdministratorService;
	
	@PostMapping(value = "/updateSystemAdmin", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PharmacySystemAdministrator> updateSystemAdministrator(@RequestBody PharmacySystemAdministrator admin){
		PharmacySystemAdministrator myAdmin = systemAdministratorService.updateSystemAdministrator(admin);
		
		if(myAdmin == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<PharmacySystemAdministrator>(myAdmin, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getAllSystemAdmins", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PharmacySystemAdministrator>> getAllSystemAdmins(){
		List<PharmacySystemAdministrator> allAdmins = systemAdministratorService.findAll();
		
		return new ResponseEntity<List<PharmacySystemAdministrator>>(allAdmins, HttpStatus.OK);
	}
	
	@PostMapping(value = "/addSystemAdmin", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PharmacySystemAdministratorDto> addSystemAdmin(@RequestBody PharmacySystemAdministrator admin){
		PharmacySystemAdministrator myAdmin = systemAdministratorService.create(admin);
		
		if(myAdmin== null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		PharmacySystemAdministratorDto myAdminDto = new PharmacySystemAdministratorDto(myAdmin);
		
		return new ResponseEntity<PharmacySystemAdministratorDto>(myAdminDto, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/getSystemAdminFromUserId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PharmacySystemAdministratorDto> getSystemAdminFromUserId(@PathVariable("id") Long id) {
		PharmacySystemAdministrator myAdmin =  systemAdministratorService.findOneByUserId(id);
		
		if(myAdmin == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		PharmacySystemAdministratorDto myAdminDto = new PharmacySystemAdministratorDto(myAdmin);
		
		return new ResponseEntity<PharmacySystemAdministratorDto>(myAdminDto, HttpStatus.OK);
	}
}
