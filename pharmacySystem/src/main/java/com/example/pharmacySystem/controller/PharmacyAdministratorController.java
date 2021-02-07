package com.example.pharmacySystem.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.pharmacySystem.dto.PharmacyAdministratorDto;
import com.example.pharmacySystem.dto.PharmacyDto;
import com.example.pharmacySystem.dto.UserDto;
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
				
		for(PharmacyAdministrator p : admins) {
			PharmacyAdministratorDto admin = new PharmacyAdministratorDto();
			admin.setId(p.getId());
			admin.setUser(new UserDto(p.getUser()));
			admin.setPharmacyDto(new PharmacyDto(p.getPharmacy()));
			
			adminsDto.add(admin);
		}
		
		return new ResponseEntity<List<PharmacyAdministratorDto>>(adminsDto, HttpStatus.OK);
	}
	
	@PostMapping(value = "/addPharmacyAdmin", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_PHARMACY_SYSTEM_ADMIN')")
	public ResponseEntity<PharmacyAdministratorDto> addPharmacyAdmin(@RequestBody PharmacyAdministrator admin){
		PharmacyAdministrator newAdmin = pharmacyAdminService.create(admin);
		
		if(newAdmin == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		PharmacyAdministratorDto adminDto = new PharmacyAdministratorDto();
		adminDto.setId(newAdmin.getId());
		adminDto.setUser(new UserDto(newAdmin.getUser()));
		adminDto.setPharmacyDto(new PharmacyDto(newAdmin.getPharmacy()));
		
		return new ResponseEntity<PharmacyAdministratorDto>(adminDto, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/getPharmacyAdmin/{adminId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PharmacyAdministratorDto> getPharmacyAdmin(@PathVariable("adminId") Long id) {
		PharmacyAdministrator admin = pharmacyAdminService.findOneById(id);
		
		if(admin == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		PharmacyAdministratorDto adminDto = new PharmacyAdministratorDto();
		adminDto.setId(admin.getId());
		adminDto.setUser(new UserDto(admin.getUser()));		
		adminDto.setPharmacyDto(new PharmacyDto(admin.getPharmacy()));

		return new ResponseEntity<PharmacyAdministratorDto>(adminDto, HttpStatus.OK);
	}
	
	@PostMapping(value = "/updatePharmacyAdmin", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_PHARMACY_ADMIN')")
	public ResponseEntity<PharmacyAdministratorDto> updatePharmacyAdmin(@RequestBody PharmacyAdministratorDto adminDto){
		PharmacyAdministrator myAdmin = pharmacyAdminService.updateAdmin(adminDto);
		
		if(myAdmin == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		PharmacyAdministratorDto myAdminDto = new PharmacyAdministratorDto(myAdmin);
		
		return new ResponseEntity<PharmacyAdministratorDto>(myAdminDto, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getPharmacyAdminFromUserId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PharmacyAdministratorDto> getPharmacyAdminByUserId(@PathVariable("id") Long id) {
		PharmacyAdministrator myAdmin = pharmacyAdminService.findOneByUserId(id);
		
		if(myAdmin == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		PharmacyAdministratorDto myAdminDto = new PharmacyAdministratorDto(myAdmin);
		
		return new ResponseEntity<PharmacyAdministratorDto>(myAdminDto, HttpStatus.OK);
	}
}
