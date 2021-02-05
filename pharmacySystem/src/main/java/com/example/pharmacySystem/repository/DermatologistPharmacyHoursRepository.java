package com.example.pharmacySystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pharmacySystem.model.DermatologistPharmacyHours;

@Repository
public interface DermatologistPharmacyHoursRepository extends JpaRepository<DermatologistPharmacyHours, Long>{

	List<DermatologistPharmacyHours> findAll();
	
	DermatologistPharmacyHours findOneById(Long id);
	
	void deleteById(Long id);
}
