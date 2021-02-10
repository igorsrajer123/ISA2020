package com.example.pharmacySystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pharmacySystem.model.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long>{

	List<Offer> findAll();
	
	Offer findOneById(Long id);
	
	List<Offer> findAllByOrderId(Long id);
}
