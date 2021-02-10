package com.example.pharmacySystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pharmacySystem.model.OrderForm;

@Repository
public interface OrderFormRepository extends JpaRepository<OrderForm, Long>{

	List<OrderForm> findAll();
	
	OrderForm findOneById(Long id);
	
	List<OrderForm> findAllByPharmacyAdministratorIdAndDeleted(Long id, boolean deleted);
}
