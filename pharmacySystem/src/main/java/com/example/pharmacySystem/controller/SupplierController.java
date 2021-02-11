package com.example.pharmacySystem.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.pharmacySystem.dto.SupplierDto;
import com.example.pharmacySystem.model.Supplier;
import com.example.pharmacySystem.service.SupplierService;

@RestController
public class SupplierController {

	@Autowired
	private SupplierService supplierService;
	
	@PostMapping(value = "/addSupplier", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_PHARMACY_SYSTEM_ADMIN')")
	public ResponseEntity<SupplierDto> addSupplier(@RequestBody Supplier supplier){
		Supplier newSupplier = supplierService.create(supplier);
		
		if(newSupplier == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		SupplierDto supplierDto = new SupplierDto(newSupplier);
		
		return new ResponseEntity<SupplierDto>(supplierDto, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/getAllSuppliers", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<SupplierDto>> getAllSuppliers(){
		List<Supplier> allSuppliers = supplierService.findAll();
		
		if(allSuppliers == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		List<SupplierDto> suppliersDto = new ArrayList<SupplierDto>();
		for(Supplier supp : allSuppliers)
			suppliersDto.add(new SupplierDto(supp));
		
		return new ResponseEntity<List<SupplierDto>>(suppliersDto, HttpStatus.OK);
	}
}
