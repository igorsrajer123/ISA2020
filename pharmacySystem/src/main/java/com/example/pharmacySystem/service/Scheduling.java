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
import com.example.pharmacySystem.model.MedicationsPharmacies;
import com.example.pharmacySystem.model.OrderForm;
import com.example.pharmacySystem.model.Patient;
import com.example.pharmacySystem.model.Promotion;
import com.example.pharmacySystem.repository.CounselingRepository;
import com.example.pharmacySystem.repository.ExaminationRepository;
import com.example.pharmacySystem.repository.MedicationReservationRepository;
import com.example.pharmacySystem.repository.MedicationsPharmaciesRepository;
import com.example.pharmacySystem.repository.OrderFormRepository;
import com.example.pharmacySystem.repository.PatientRepository;
import com.example.pharmacySystem.repository.PromotionRepository;

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
	
	@Autowired
	private PromotionRepository promotionRepository;
	
	@Autowired
	private MedicationsPharmaciesRepository medsInPharmaciesRepository;
	
	@Autowired
	private OrderFormRepository orderRepository;
	
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
	
	@Scheduled(initialDelay = 5000L, fixedDelay = 5000L)
	void deleteExpiredPromotions() {
		List<Promotion> allPromotions = promotionRepository.findAll();
		
		for(Promotion p : allPromotions)
			if(java.sql.Date.valueOf(p.getUntilDate()).before(java.sql.Date.valueOf(LocalDate.now()))) {
				System.out.println("Promotion deleted!");
				p.setDeleted(true);
				promotionRepository.save(p);
			}
	}
	
	@Scheduled(initialDelay = 10000L, fixedDelay = 10000L)
	void medicationsPriceExpires() {
		List<MedicationsPharmacies> allMedsInPharmacies = medsInPharmaciesRepository.findAll();
		
		for(MedicationsPharmacies mp : allMedsInPharmacies) {
			if(java.sql.Date.valueOf(mp.getPriceExpiringDate()).before(java.sql.Date.valueOf(LocalDate.now()))) {
				System.out.println("Med deleted!");
				mp.setDeleted(true);
				medsInPharmaciesRepository.save(mp);
			}
		}
	}
	
	@Scheduled(initialDelay = 10000L, fixedDelay = 10000L)
	void orderFormExpires() {
		List<OrderForm> orders = orderRepository.findAll();
		
		for(OrderForm o : orders) {
			if(java.sql.Date.valueOf(o.getUntilDate()).before(java.sql.Date.valueOf(LocalDate.now()))) {
				System.out.println("Order deleted!");
				o.setDeleted(true);
				orderRepository.save(o);
			}
		}
	}

}
