package com.example.pharmacySystem.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pharmacySystem.model.MedicationsToOrder;

@Repository
public interface MedicationsToOrderRepository extends JpaRepository<MedicationsToOrder, Long>{

	List<MedicationsToOrder> findAll();
	
	MedicationsToOrder findOneById(Long id);
	
	List<MedicationsToOrder> findAllByOrderFormIdAndDeleted(Long id, boolean deleted);
}
