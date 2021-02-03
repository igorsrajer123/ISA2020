package com.example.pharmacySystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pharmacySystem.model.Dermatologist;

@Repository
public interface DermatologistRepository extends JpaRepository<Dermatologist, Long>{

	List<Dermatologist> findAll();
	
	Dermatologist findOneById(Long id);
}
