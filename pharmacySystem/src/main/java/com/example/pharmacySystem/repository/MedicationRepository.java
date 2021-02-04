package com.example.pharmacySystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.pharmacySystem.model.Medication;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long>{

	List<Medication> findAll();
	
	Medication findOneById(Long id);
	
	List<Medication> findAllByName(String name);
}
