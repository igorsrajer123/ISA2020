package com.example.pharmacySystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pharmacySystem.model.Counseling;

@Repository
public interface CounselingRepository extends JpaRepository<Counseling, Long>{

	List<Counseling> findAll();
	
	Counseling findOneById(Long id);
	
	List<Counseling> findAllByPharmacistId(Long id);
	
	List<Counseling> findAllByPatientIdAndStatus(Long id, String status);
}
