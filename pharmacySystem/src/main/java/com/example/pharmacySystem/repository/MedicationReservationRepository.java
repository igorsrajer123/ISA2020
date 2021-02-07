package com.example.pharmacySystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pharmacySystem.model.MedicationReservation;

@Repository
public interface MedicationReservationRepository extends JpaRepository<MedicationReservation, Long>{

	List<MedicationReservation> findAll();
	
	MedicationReservation findOneById(Long id);
	
	List<MedicationReservation> findAllByPatientId(Long id);
	
	List<MedicationReservation> findAllByMedicationFromPharmacyId(Long id);
	
	List<MedicationReservation> findAllByPatientIdAndStatus(Long id, String status);
}
