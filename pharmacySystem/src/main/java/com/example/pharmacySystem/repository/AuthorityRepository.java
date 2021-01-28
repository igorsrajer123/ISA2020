package com.example.pharmacySystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pharmacySystem.model.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long>{

	Authority findOneById(Long id);
	
	Authority findOneByName(String name);
}
