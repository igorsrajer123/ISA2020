package com.example.pharmacySystem.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pharmacySystem.dto.CounselingDto;
import com.example.pharmacySystem.model.Absence;
import com.example.pharmacySystem.model.Counseling;
import com.example.pharmacySystem.model.Patient;
import com.example.pharmacySystem.model.Pharmacist;
import com.example.pharmacySystem.repository.CounselingRepository;
import com.example.pharmacySystem.repository.PatientRepository;
import com.example.pharmacySystem.repository.PharmacistRepository;

@Service
public class CounselingService {

	@Autowired
	private CounselingRepository counselingRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private PharmacistRepository pharmacistRepository;
	
	public Counseling create(CounselingDto counselingDto) {
		Counseling newCounseling = new Counseling();
		Patient p = patientRepository.findOneById(counselingDto.getPatient().getId());
		
		for(Counseling c : p.getCounselings()) {
			if(c.getPharmacist().getId() == counselingDto.getPharmacist().getId()) {
				if(c.getStatus().equals("ACTIVE")) {
					System.out.println("Vec ima zakazan!");
					return null;
				}
			}
		}
		
		boolean hasAbsences = false;
		boolean hasActiveAbsences = false;
		Pharmacist pharmacist = pharmacistRepository.findOneById(counselingDto.getPharmacist().getId());
		if(!pharmacist.getAbsences().isEmpty()) 
			hasAbsences = true;
		
		if(hasAbsences) {
			for(Absence a : pharmacist.getAbsences()) 
				if(a.getStatus().equals("ACCEPTED"))
					hasActiveAbsences = true;
			
			if(hasActiveAbsences) {
				for(Absence a : pharmacist.getAbsences()) {
					if(a.getStatus().equals("ACCEPTED")) {
						LocalDate absenceFromDate = a.getFrom();
						Date absenceFrom = java.sql.Date.valueOf(absenceFromDate);
						
						Calendar calendar1 = Calendar.getInstance();
						calendar1.setTime(absenceFrom);
						calendar1.set(Calendar.HOUR_OF_DAY, 0);
						calendar1.set(Calendar.MINUTE, 0);
						calendar1.set(Calendar.SECOND, 0);
						calendar1.set(Calendar.MILLISECOND, 0);
						Date date1 = calendar1.getTime();
						
						LocalDate absenceUntilDate = a.getUntil();
						Date absenceUntil = java.sql.Date.valueOf(absenceUntilDate);
						
						Calendar calendar2 = Calendar.getInstance();
						calendar2.setTime(absenceUntil);
						calendar2.set(Calendar.HOUR_OF_DAY, 0);
						calendar2.set(Calendar.MINUTE, 0);
						calendar2.set(Calendar.SECOND, 0);
						calendar2.set(Calendar.MILLISECOND, 0);
						Date date2 = calendar2.getTime();
						
						LocalDate counselingDate = counselingDto.getDate();
						Date counselingD = java.sql.Date.valueOf(counselingDate);
						
						Calendar calendar3 = Calendar.getInstance();
						calendar3.setTime(counselingD);
						calendar3.set(Calendar.HOUR_OF_DAY, 0);
						calendar3.set(Calendar.MINUTE, 0);
						calendar3.set(Calendar.SECOND, 0);
						calendar3.set(Calendar.MILLISECOND, 0);
						Date date3 = calendar3.getTime();
						System.out.println(date1);
						System.out.println(date2);
						System.out.println(date3   );
						if(date3.equals(date1) || date3.equals(date2) || (date3.after(date1) && date3.before(date2))) {
							System.out.println("na godisnjem!");
							Counseling newC = new Counseling();
							newC.setId(Long.valueOf(-1));
							return newC;
						}else {
							System.out.println("Uspesno kreiran!");
							newCounseling.setDate(counselingDto.getDate());
							newCounseling.setFrom(counselingDto.getFrom());
							newCounseling.setTo(counselingDto.getTo());
							newCounseling.setStatus("ACTIVE");
							newCounseling.setPatient(patientRepository.findOneById(counselingDto.getPatient().getId()));
							newCounseling.setPharmacist(pharmacistRepository.findOneById(counselingDto.getPharmacist().getId()));
							counselingRepository.save(newCounseling);
							return newCounseling;
						}
					}
				}
			}else {
				System.out.println("Uspesno kreiran!");
				newCounseling.setDate(counselingDto.getDate());
				newCounseling.setFrom(counselingDto.getFrom());
				newCounseling.setTo(counselingDto.getTo());
				newCounseling.setStatus("ACTIVE");
				newCounseling.setPatient(patientRepository.findOneById(counselingDto.getPatient().getId()));
				newCounseling.setPharmacist(pharmacistRepository.findOneById(counselingDto.getPharmacist().getId()));
				counselingRepository.save(newCounseling);
				return newCounseling;
			}
		}else {
			System.out.println("Uspesno kreiran!");
			newCounseling.setDate(counselingDto.getDate());
			newCounseling.setFrom(counselingDto.getFrom());
			newCounseling.setTo(counselingDto.getTo());
			newCounseling.setStatus("ACTIVE");
			newCounseling.setPatient(patientRepository.findOneById(counselingDto.getPatient().getId()));
			newCounseling.setPharmacist(pharmacistRepository.findOneById(counselingDto.getPharmacist().getId()));
			counselingRepository.save(newCounseling);
			return newCounseling;
		}
		System.out.println("Nista od navedenog!");
		return newCounseling;
	}
	
	public List<Counseling> getPatientActiveCounselings(Long patientId){
		Patient p = patientRepository.findOneById(patientId);
		List<Counseling> patientsCounselings = p.getCounselings();
		List<Counseling> retVal = new ArrayList<Counseling>();
		
		for(Counseling c : patientsCounselings) 
			if(c.getStatus().equals("ACTIVE"))
				retVal.add(c);
		
		return retVal;
	}
	
	public Counseling cancelCounseling(Long counselingId) {
		Counseling c = counselingRepository.findOneById(counselingId);
		Pharmacist p = pharmacistRepository.findOneById(c.getPharmacist().getId());
		List<Counseling> pharmacistCounselings = p.getCounselings();
		
		LocalDate counselingEndDate = c.getDate();
		Date counselingDate = java.sql.Date.valueOf(counselingEndDate);
		
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(counselingDate);
		calendar1.set(Calendar.HOUR_OF_DAY, c.getTo());
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
		
		System.out.println(date1);
		System.out.println(date2);
		
		if(c.getStatus().equals("ACTIVE")) {
			if(Math.abs(date1.getTime() - date2.getTime()) > MILLIS_PER_DAY) {
				System.out.println("Counseling can be cancelled!");
				c.setStatus("CANCELLED");		
				
				for(Counseling counseling : pharmacistCounselings) {
					if(counseling.getId() == c.getId()) {
						counseling.setStatus("CANCELLED");
						System.out.println("CANCELLED!");
					}
				}
				
				counselingRepository.save(c);
				return c;
			}else {
				System.out.println("Cannot cancel counseling!");
				return null;
			}
		}
		
		return c;
	}
	
	public List<Counseling> findAllByPharmacistId(Long id){
		return counselingRepository.findAllByPharmacistId(id);
	}
	
	public List<Counseling> findAllByPatientIdAndStatus(Long id, String status){
		return counselingRepository.findAllByPatientIdAndStatus(id, status);
	}
}
