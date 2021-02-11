package com.example.pharmacySystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pharmacySystem.model.Absence;
import com.example.pharmacySystem.model.Dermatologist;
import com.example.pharmacySystem.model.Pharmacist;
import com.example.pharmacySystem.model.Pharmacy;
import com.example.pharmacySystem.repository.AbsenceRepository;
import com.example.pharmacySystem.repository.DermatologistRepository;
import com.example.pharmacySystem.repository.PharmacyRepository;

@Service
public class AbsenceService {

	@Autowired
	private AbsenceRepository absenceRepository;
	
	@Autowired
	private DermatologistRepository dermatologistRepository;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	public List<Absence> getAllPharmacistsAbsences(Long pharmacyId){
		Pharmacy myPharmacy = pharmacyRepository.findOneById(pharmacyId);
		List<Pharmacist> pharmacists = myPharmacy.getPharmacists();
		List<Absence> absences = new ArrayList<Absence>();
		
		for(Pharmacist p : pharmacists) 
			for(Absence a : p.getAbsences())
				absences.add(a);
		
		return absences;
	}
	
	public List<Absence> getAllDermatologistAbsences(){
		List<Dermatologist> dermatologists = dermatologistRepository.findAll();
		List<Absence> absences = new ArrayList<Absence>();
		
		for(Dermatologist d : dermatologists)
			for(Absence a : d.getAbsences())
					absences.add(a);
		
		return absences;
	}
	
	public Absence declineAbsence(Long absenceId) {
		Absence a = absenceRepository.findOneById(absenceId);
		a.setStatus("DECLINED");
		absenceRepository.save(a);
		return a;
	}
	
	public Absence acceptAbsence(Long absenceId) {
		Absence a = absenceRepository.findOneById(absenceId);
		a.setStatus("ACCEPTED");
		absenceRepository.save(a);
		return a;
	}
}
