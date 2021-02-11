package com.example.pharmacySystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pharmacySystem.model.Absence;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long>{

	List<Absence> findAll();
	
	Absence findOneById(Long id);
}
