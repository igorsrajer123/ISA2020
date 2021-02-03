package com.example.pharmacySystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pharmacySystem.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>{

	List<Patient> findAll();
	
	Patient findOneById(Long id);
	
	Patient findOneByUserId(Long id);	
}
