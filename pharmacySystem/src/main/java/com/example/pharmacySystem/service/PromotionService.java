package com.example.pharmacySystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pharmacySystem.dto.PromotionDto;
import com.example.pharmacySystem.model.Promotion;
import com.example.pharmacySystem.repository.PharmacyRepository;
import com.example.pharmacySystem.repository.PromotionRepository;

@Service
public class PromotionService {

	@Autowired
	private PromotionRepository promotionRepository;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	public List<Promotion> findAll(){
		return promotionRepository.findAll();
	}
	
	public Promotion findOneById(Long id) {
		return promotionRepository.findOneById(id);
	}
	
	public List<Promotion> findAllByPharmacyId(Long id){
		return promotionRepository.findAllByPharmacyId(id);
	}
	
	public Promotion createPromotion(PromotionDto promotion) {
		Promotion newPromo = new Promotion();
		newPromo.setText(promotion.getText());
		newPromo.setUntilDate(promotion.getUntilDate());
		newPromo.setPharmacy(pharmacyRepository.findOneById(promotion.getPharmacy().getId()));
		promotionRepository.save(newPromo);
		return newPromo;
	}
}
