package com.example.pharmacySystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pharmacySystem.model.PharmacyAdministrator;

@Repository
public interface PharmacyAdministratorRepository extends JpaRepository<PharmacyAdministrator, Long>{

	List<PharmacyAdministrator> findAll();
	
	PharmacyAdministrator findOneById(Long id);
}
