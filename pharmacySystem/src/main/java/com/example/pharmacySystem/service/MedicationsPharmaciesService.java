package com.example.pharmacySystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pharmacySystem.dto.MedicationsPharmaciesDto;
import com.example.pharmacySystem.model.MedicationReservation;
import com.example.pharmacySystem.model.MedicationsPharmacies;
import com.example.pharmacySystem.repository.MedicationReservationRepository;
import com.example.pharmacySystem.repository.MedicationsPharmaciesRepository;

@Service
public class MedicationsPharmaciesService {

	@Autowired
	private MedicationsPharmaciesRepository medicationsPharmaciesRepository;
	
	@Autowired
	private MedicationReservationRepository reservationRepository;
	
	public List<MedicationsPharmacies> findAll(){
		return medicationsPharmaciesRepository.findAll();
	}
	
	public MedicationsPharmacies findOneById(Long id) {
		return medicationsPharmaciesRepository.findOneById(id);
	}
	
	public List<MedicationsPharmacies> findAllByDeleted(boolean deleted){
		return medicationsPharmaciesRepository.findAllByDeleted(deleted);
	}
	
	public List<MedicationsPharmacies> findAllByPharmacyId(Long id){
		return medicationsPharmaciesRepository.findAllByPharmacyId(id);
	}
	
	public MedicationsPharmacies findOneByPharmacyIdAndMedicationIdAndDeleted(Long pharmacyId, Long medicationId, boolean deleted){
		return medicationsPharmaciesRepository.findOneByPharmacyIdAndMedicationIdAndDeleted(pharmacyId, medicationId, deleted);
	}
	
	public MedicationsPharmacies updateMedicationInPharmacy(MedicationsPharmaciesDto mpDto) {
		MedicationsPharmacies asd = findOneByPharmacyIdAndMedicationIdAndDeleted(mpDto.getPharmacy().getId(), mpDto.getMedication().getId(), false);
		asd.setAmount(mpDto.getAmount());
		asd.setPrice(mpDto.getPrice());
		medicationsPharmaciesRepository.save(asd);
		return asd;		
	}
	
	public MedicationsPharmacies getMedicationFromReservation(Long reservationId) {
		MedicationReservation reservation = reservationRepository.findOneById(reservationId);
		MedicationsPharmacies mp = medicationsPharmaciesRepository.findOneById(reservation.getMedicationFromPharmacy().getId());
		return mp;
	}
}
