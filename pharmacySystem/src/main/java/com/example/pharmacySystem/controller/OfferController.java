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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pharmacySystem.dto.OfferDto;
import com.example.pharmacySystem.model.Offer;
import com.example.pharmacySystem.service.OfferService;

@RestController
public class OfferController {

	@Autowired
	private OfferService offerService;
	
	@GetMapping(value = "/getOrderOffers/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<OfferDto>> getOrderOffers(@PathVariable("orderId") Long id){
		List<Offer> offers = offerService.findAllByOrderId(id);
		
		if(offers == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		List<OfferDto> offersDto = new ArrayList<OfferDto>();
		for(Offer o : offers)
			offersDto.add(new OfferDto(o));
		
		return new ResponseEntity<List<OfferDto>>(offersDto, HttpStatus.OK);
	}
	
	@PutMapping(value = "/acceptOffer/{offerId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_PHARMACY_ADMIN')")
	public ResponseEntity<OfferDto> acceptOffer(@PathVariable("offerId") Long id){
		Offer offer = offerService.acceptOffer(id);
		
		if(offer == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		OfferDto offerDto = new OfferDto(offer);
		
		return new ResponseEntity<OfferDto>(offerDto, HttpStatus.OK);
	}
}
