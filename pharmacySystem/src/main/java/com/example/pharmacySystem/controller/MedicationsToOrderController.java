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
import com.example.pharmacySystem.dto.MedicationsToOrderDto;
import com.example.pharmacySystem.model.MedicationsToOrder;
import com.example.pharmacySystem.service.MedicationsToOrderService;

@RestController
public class MedicationsToOrderController {

	@Autowired
	private MedicationsToOrderService medsToOrderService;
	
	@PostMapping(value = "/createOrderItem", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MedicationsToOrderDto> createOrderItem(@RequestBody MedicationsToOrderDto dto){
		MedicationsToOrder newOrderItem = medsToOrderService.createOrderItem(dto);
		
		if(newOrderItem == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		MedicationsToOrderDto newDto = new MedicationsToOrderDto(newOrderItem);
		
		return new ResponseEntity<MedicationsToOrderDto>(newDto, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getOrderItems/{formId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MedicationsToOrderDto>> getOrderItems(@PathVariable("formId") Long id){
		List<MedicationsToOrder> items = medsToOrderService.findAllByOrderFormIdAndDeleted(id, false);
		
		if(items == null) return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		List<MedicationsToOrderDto> itemsDto = new ArrayList<MedicationsToOrderDto>();
		for(MedicationsToOrder m : items)
			itemsDto.add(new MedicationsToOrderDto(m));
		
		return new ResponseEntity<List<MedicationsToOrderDto>>(itemsDto, HttpStatus.OK);
	}
}
