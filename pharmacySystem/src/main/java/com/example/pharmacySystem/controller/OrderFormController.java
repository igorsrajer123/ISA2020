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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.pharmacySystem.dto.OrderFormDto;
import com.example.pharmacySystem.model.OrderForm;
import com.example.pharmacySystem.service.OrderFormService;

@RestController
public class OrderFormController {

	@Autowired
	private OrderFormService orderService;
	
	@PostMapping(value = "/createOrder", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_PHARMACY_ADMIN')")
	public ResponseEntity<OrderFormDto> createOrder(@RequestBody OrderFormDto orderDto){
		OrderForm newOrder = orderService.createOrder(orderDto);
		
		if(newOrder == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		OrderFormDto newOrderDto = new OrderFormDto(newOrder);
		
		return new ResponseEntity<OrderFormDto>(newOrderDto, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getPharmacyAdminOrders/{adminId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<OrderFormDto>> getPatientActiveCounselings(@PathVariable("adminId") Long adminId){
		List<OrderForm> adminsOrders = orderService.findAllByPharmacyAdministratorIdAndDeleted(adminId, false);
		
		if(adminsOrders == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		List<OrderFormDto> ordersDto = new ArrayList<OrderFormDto>();
		for(OrderForm of : adminsOrders)
			ordersDto.add(new OrderFormDto(of));
		
		return new ResponseEntity<>(ordersDto, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getPharmacyOrders/{pharmacyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<OrderFormDto>> getPharmacyOrders(@PathVariable("pharmacyId") Long pharmacyId){
		List<OrderForm> pharmacyOrders = orderService.getPharmacyOrders(pharmacyId);
		
		if(pharmacyOrders == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		List<OrderFormDto> ordersDto = new ArrayList<OrderFormDto>();
		for(OrderForm of : pharmacyOrders)
			ordersDto.add(new OrderFormDto(of));
		
		return new ResponseEntity<>(ordersDto, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/deleteOrder/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_PHARMACY_ADMIN')")
	public ResponseEntity<OrderFormDto> deleteOrder(@PathVariable("orderId") Long orderId){
		OrderForm of = orderService.deleteOrder(orderId);
		
		if(of == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		OrderFormDto newOrderDto = new OrderFormDto(of);
		
		return new ResponseEntity<OrderFormDto>(newOrderDto, HttpStatus.OK);
	}
	
	@PutMapping(value = "/updateOrder", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_PHARMACY_ADMIN')")
	public ResponseEntity<OrderFormDto> updateOrder(@RequestBody OrderFormDto orderDto){
		OrderForm order = orderService.updateOrder(orderDto);
		
		if(order == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		OrderFormDto newOrderDto = new OrderFormDto(order);
		
		return new ResponseEntity<OrderFormDto>(newOrderDto, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getOrderById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrderFormDto> getOrderById(@PathVariable("id") Long id){
		OrderForm order = orderService.findOneById(id);
		
		if(order == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		OrderFormDto newOrderDto = new OrderFormDto(order);
		
		return new ResponseEntity<OrderFormDto>(newOrderDto, HttpStatus.OK);
	}
}
