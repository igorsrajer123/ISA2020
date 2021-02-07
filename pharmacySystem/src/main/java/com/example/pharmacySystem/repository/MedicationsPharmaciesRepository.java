package com.example.pharmacySystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pharmacySystem.model.MedicationsPharmacies;

@Repository
public interface MedicationsPharmaciesRepository extends JpaRepository<MedicationsPharmacies, Long>{

	List<MedicationsPharmacies> findAll();
	
	MedicationsPharmacies findOneById(Long id);
	
	List<MedicationsPharmacies> findAllByPharmacyId(Long id);
	
	MedicationsPharmacies findOneByPharmacyIdAndMedicationIdAndDeleted(Long pharmacyId, Long medicationId, boolean deleted);	
}
