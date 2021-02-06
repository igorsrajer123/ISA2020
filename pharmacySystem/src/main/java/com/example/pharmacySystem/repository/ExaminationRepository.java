package com.example.pharmacySystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pharmacySystem.model.Examination;

@Repository
public interface ExaminationRepository extends JpaRepository<Examination, Long>{

	List<Examination> findAll();
	
	Examination findOneById(Long id);
	
	List<Examination> findAllByDermatologistId(Long id);	
	
	List<Examination> findAllByStatusAndDermatologistIdAndPharmacyId(String status, Long dermatologistId, Long pharmacyId);
	
	List<Examination> findAllByDermatologistIdAndPharmacyId(Long dermatologistId, Long pharmacyId);
}
