package com.example.pharmacySystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pharmacySystem.model.Pharmacy;

@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, Long>{

	List<Pharmacy> findAll();
	
	Pharmacy findOneById(Long id);
	
	Pharmacy findOneByName(String name);
	
}
