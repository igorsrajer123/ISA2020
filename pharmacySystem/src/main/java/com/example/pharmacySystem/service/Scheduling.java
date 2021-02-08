package com.example.pharmacySystem.service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.pharmacySystem.model.Counseling;
import com.example.pharmacySystem.model.Examination;
import com.example.pharmacySystem.model.MedicationReservation;
import com.example.pharmacySystem.model.Patient;
import com.example.pharmacySystem.repository.CounselingRepository;
import com.example.pharmacySystem.repository.ExaminationRepository;
import com.example.pharmacySystem.repository.MedicationReservationRepository;
import com.example.pharmacySystem.repository.PatientRepository;

@Component
public class Scheduling {
	         
	@Autowired
	private CounselingRepository counselingRepository;
	
	@Autowired
	private ExaminationRepository examinationRepository;
	
	@Autowired
	private MedicationReservationRepository reservationRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Scheduled(initialDelay = 5000L, fixedDelay = 10000L)
	void patientCounselingsDone() {
		List<Counseling> allCounselings = counselingRepository.findAll();
		
		for(Counseling c : allCounselings) {
			if(c.getStatus().equals("ACTIVE")) {
				if(java.sql.Date.valueOf(c.getDate()).before(java.sql.Date.valueOf(LocalDate.now()))) {
					c.setStatus("DONE");
					counselingRepository.save(c);
				}
			}
		}
	}
	
	@Scheduled(initialDelay = 6000L, fixedDelay = 10000L)
	void patientExaminationsDone() {
		List<Examination> allExaminations = examinationRepository.findAll();
		
		for(Examination ex : allExaminations)
			if(java.sql.Date.valueOf(ex.getDate()).before(java.sql.Date.valueOf(LocalDate.now())))
				if(ex.getStatus().equals("ACTIVE")) {
					ex.setStatus("DONE");
					examinationRepository.save(ex);
				}else if(ex.getStatus().equals("FREE")) {
					ex.setStatus("DELETED");
					examinationRepository.save(ex);
				}		
	}
	
	@Scheduled(initialDelay = 2000L, fixedDelay = 10000L)
	void patientMedicationReservationsDone() {
		List<MedicationReservation> allReservations = reservationRepository.findAll();
		
		for(MedicationReservation r : allReservations)
			if(java.sql.Date.valueOf(r.getPickUpDate()).before(java.sql.Date.valueOf(LocalDate.now())))
				if(r.getStatus().equals("ACTIVE")) {
					r.setStatus("DELETED");
					Patient myPatient = patientRepository.findOneById(r.getPatient().getId());
					myPatient.setPenalties(myPatient.getPenalties() + 1);
					reservationRepository.save(r);
					patientRepository.save(myPatient);
				}
	}
	
	@Scheduled(initialDelay = 10000L, fixedDelay = 10000L)
	void resetPatientsPenalties() {
		List<Patient> allPatients = patientRepository.findAll();
		
		Date now = java.sql.Date.valueOf(LocalDate.now());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		
		if(calendar.get(Calendar.DAY_OF_MONTH) == 1)
			for(Patient p : allPatients) {			
				p.setPenalties(0);
				patientRepository.save(p);
			}
	}

}
