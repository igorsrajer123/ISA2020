package com.example.pharmacySystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.pharmacySystem.model.PharmacySystemAdministrator;

@Repository
public interface PharmacySystemAdministratorRepository extends JpaRepository<PharmacySystemAdministrator, Long>{
	
	List<PharmacySystemAdministrator> findAll();
	
	PharmacySystemAdministrator findOneById(Long id);
	
	PharmacySystemAdministrator findOneByUserId(Long id);
	
	@SuppressWarnings("unchecked")
	PharmacySystemAdministrator save(PharmacySystemAdministrator patient);
}
