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
import com.example.pharmacySystem.dto.PromotionDto;
import com.example.pharmacySystem.model.Promotion;
import com.example.pharmacySystem.service.PromotionService;

@RestController
public class PromotionController {

	@Autowired
	private PromotionService promotionService;
	
	@GetMapping(value = "/getPharmacyPromotions/{pharmacyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PromotionDto>> getPharmacyPromotions(@PathVariable("pharmacyId") Long pharmacyId) {
		List<Promotion> pharmacyPromos = promotionService.findAllByPharmacyId(pharmacyId);
		
		if(pharmacyPromos == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		List<PromotionDto> promos = new ArrayList<PromotionDto>();
		for(Promotion prom : pharmacyPromos)
			promos.add(new PromotionDto(prom));
		
		return new ResponseEntity<List<PromotionDto>>(promos, HttpStatus.OK);
	}
	
	@PostMapping(value = "/createPromotion", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PromotionDto> createPromotion(@RequestBody PromotionDto promotionDto){
		Promotion newPromo = promotionService.createPromotion(promotionDto);
		
		if(newPromo == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	
		PromotionDto promoDto = new PromotionDto(newPromo);
		
		return new ResponseEntity<PromotionDto>(promoDto, HttpStatus.CREATED);
	}
}
