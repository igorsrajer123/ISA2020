package com.example.pharmacySystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pharmacySystem.model.MedicationType;

@Repository
public interface MedicationTypeRepository extends JpaRepository<MedicationType, Long>{

	List<MedicationType> findAll();
	
	MedicationType findOneById(Long id);
	
	MedicationType findOneByName(String name);
}
