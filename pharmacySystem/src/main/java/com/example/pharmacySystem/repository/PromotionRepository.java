package com.example.pharmacySystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pharmacySystem.model.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long>{

	List<Promotion> findAll();
	
	Promotion findOneById(Long id);
	
	List<Promotion> findAllByPharmacyIdAndDeleted(Long id, boolean deleted);
}
