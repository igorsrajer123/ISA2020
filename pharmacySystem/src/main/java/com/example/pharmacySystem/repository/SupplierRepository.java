package com.example.pharmacySystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pharmacySystem.model.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long>{

	List<Supplier> findAll();
	
	Supplier findOneById(Long id);
}
