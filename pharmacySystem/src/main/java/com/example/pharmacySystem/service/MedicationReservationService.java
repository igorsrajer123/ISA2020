package com.example.pharmacySystem.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pharmacySystem.model.MedicationReservation;
import com.example.pharmacySystem.model.MedicationsPharmacies;
import com.example.pharmacySystem.model.Patient;
import com.example.pharmacySystem.repository.MedicationReservationRepository;
import com.example.pharmacySystem.repository.MedicationsPharmaciesRepository;
import com.example.pharmacySystem.repository.PatientRepository;

@Service
public class MedicationReservationService {

	@Autowired
	private MedicationReservationRepository medicationReservationRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private MedicationsPharmaciesRepository medicationsPharmaciesRepository;
	
	public MedicationReservation create(MedicationReservation mr) {
		MedicationReservation newMR = new MedicationReservation();
		newMR.setPickUpDate(mr.getPickUpDate());
		newMR.setStatus("ACTIVE");
		newMR.setPatient(patientRepository.findOneById(mr.getPatient().getId()));
		MedicationsPharmacies medicationInPharmacy = medicationsPharmaciesRepository.findOneById(mr.getMedicationFromPharmacy().getId());
		medicationInPharmacy.setAmount(medicationInPharmacy.getAmount() - 1);
		newMR.setMedicationFromPharmacy(medicationsPharmaciesRepository.findOneById(mr.getMedicationFromPharmacy().getId()));			
		medicationReservationRepository.save(newMR);
		
		return newMR;
	}
	
	public List<MedicationReservation> getPatientReservations(Long patientId){
		Patient p = patientRepository.findOneById(patientId);
		
		List<MedicationReservation> patientReservations = p.getMedicationReservations();
		
		return patientReservations;
	}
	
	public List<MedicationReservation> findAllByPatientIdAndStatus(Long id, String status){
		return medicationReservationRepository.findAllByPatientIdAndStatus(id, status);
	}
	
	public MedicationReservation cancelReservation(Long patientId, Long reservationId) {
		MedicationReservation mr = medicationReservationRepository.findOneById(reservationId);
		
		LocalDate reservationEndDate = mr.getPickUpDate();
		Date reservationDate = java.sql.Date.valueOf(reservationEndDate);
		
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(reservationDate);
		calendar1.set(Calendar.HOUR_OF_DAY, 0);
		calendar1.set(Calendar.MINUTE, 0);
		calendar1.set(Calendar.SECOND, 0);
		calendar1.set(Calendar.MILLISECOND, 0);
		Date date1 = calendar1.getTime();
		
		Date now = java.sql.Date.valueOf(LocalDate.now());
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(now);
		calendar2.set(Calendar.HOUR_OF_DAY, LocalDateTime.now().getHour());
		calendar2.set(Calendar.MINUTE, LocalDateTime.now().getMinute());
		calendar2.set(Calendar.SECOND, 0);
		calendar2.set(Calendar.MILLISECOND, 0);
		Date date2 = calendar2.getTime();
		long MILLIS_PER_DAY = 24 * 60 * 60 * 1000L;
		
		if(mr.getStatus().equals("ACTIVE")) {
			if(Math.abs(date1.getTime() - date2.getTime()) >= MILLIS_PER_DAY) {
				System.out.println("Reservation can be cancelled!");
				mr.setStatus("CANCELLED");
				Patient p = patientRepository.findOneById(patientId);
				p.getMedicationReservations().remove(mr);
				MedicationsPharmacies mp = medicationsPharmaciesRepository.findOneById(mr.getMedicationFromPharmacy().getId());
				List<MedicationReservation> medicationsReserved = mp.getMedicationReservations();
				for(MedicationReservation medRes : medicationsReserved) {
					if(medRes.getId() == mr.getId()) {
						medRes.setStatus("CANCELLED");
						medicationReservationRepository.save(mr);
					}
				}
			}else {
				System.out.println("Cannot cancel reservation!");
				return null;
			}
		}
		return mr;
	}
	
	public MedicationReservation acquireMedication(Long patientId, Long reservationId) {
		MedicationReservation mr = medicationReservationRepository.findOneById(reservationId);
		
		LocalDate reservationEndDate = mr.getPickUpDate();
		Date reservationDate = java.sql.Date.valueOf(reservationEndDate);
		
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(reservationDate);
		calendar1.set(Calendar.HOUR_OF_DAY, 0);
		calendar1.set(Calendar.MINUTE, 0);
		calendar1.set(Calendar.SECOND, 0);
		calendar1.set(Calendar.MILLISECOND, 0);
		Date date1 = calendar1.getTime();
		
		Date now = java.sql.Date.valueOf(LocalDate.now());
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(now);
		calendar2.set(Calendar.HOUR_OF_DAY, LocalDateTime.now().getHour());
		calendar2.set(Calendar.MINUTE, LocalDateTime.now().getMinute());
		calendar2.set(Calendar.SECOND, 0);
		calendar2.set(Calendar.MILLISECOND, 0);
		Date date2 = calendar2.getTime();
	
		if(mr.getStatus().equals("ACTIVE")) {
			if(date2.before(date1)) {
				System.out.println("Med can be acquired!");
				mr.setStatus("DONE");
				Patient p = patientRepository.findOneById(patientId);
				p.getMedicationReservations().remove(mr);
				MedicationsPharmacies mp = medicationsPharmaciesRepository.findOneById(mr.getMedicationFromPharmacy().getId());
				List<MedicationReservation> medicationsReserved = mp.getMedicationReservations();
				for(MedicationReservation medRes : medicationsReserved) {
					if(medRes.getId() == mr.getId()) {
						medRes.setStatus("DONE");
						medicationReservationRepository.save(mr);
					}
				}
			}else {
				System.out.println("Cannot acquire med!");
				return null;
			}
		}
		return mr;
	}
}
