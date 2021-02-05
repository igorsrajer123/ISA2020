package com.example.pharmacySystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.pharmacySystem.dto.DermatologistPharmacyHoursDto;
import com.example.pharmacySystem.model.Dermatologist;
import com.example.pharmacySystem.model.DermatologistPharmacyHours;
import com.example.pharmacySystem.model.Pharmacy;
import com.example.pharmacySystem.repository.DermatologistPharmacyHoursRepository;
import com.example.pharmacySystem.repository.DermatologistRepository;
import com.example.pharmacySystem.repository.PharmacyRepository;

@Service
public class DermatologistPharmacyHoursService {

	@Autowired
	private DermatologistPharmacyHoursRepository repository;
	
	@Autowired
	private DermatologistRepository dermatologistRepository;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	public List<DermatologistPharmacyHours> findAll(){
		return repository.findAll();
	}
	
	public DermatologistPharmacyHours findOneById(Long id) {
		return repository.findOneById(id);
	}

	public List<DermatologistPharmacyHours> getDermatologistActiveWorkingHours(Long dermatologistId){
		Dermatologist dermatologist = dermatologistRepository.findOneById(dermatologistId);
		List<DermatologistPharmacyHours> dermatologistWorkingHours = dermatologist.getPharmacyHours();
		List<DermatologistPharmacyHours> dermatologistActiveWorkingHours = new ArrayList<DermatologistPharmacyHours>();
		
		for(DermatologistPharmacyHours h : dermatologistWorkingHours)
			if(!h.isDeleted())
				dermatologistActiveWorkingHours.add(h);
		
		return dermatologistActiveWorkingHours;
	}
	
	public DermatologistPharmacyHours addDermatologistWorkingHours(DermatologistPharmacyHoursDto hours) {
		Dermatologist dermatologist = dermatologistRepository.findOneById(hours.getDermatologist().getId());
		Pharmacy pharmacy = pharmacyRepository.findOneById(hours.getPharmacy().getId());
		DermatologistPharmacyHours newHours = new DermatologistPharmacyHours();
		newHours.setFrom(hours.getFrom());
		newHours.setTo(hours.getTo());
		newHours.setDermatologist(dermatologist);
		newHours.setPharmacy(pharmacy);
		newHours.setDeleted(false);
		dermatologist.getPharmacyHours().add(newHours);
		pharmacy.getDermatologistHours().add(newHours);
		repository.save(newHours);
		
		return newHours;
	}
	
	public DermatologistPharmacyHours removeDermatologistWorkingHoursFromPharmacy(Long dermatologistId, Long pharmacyId) {
		List<DermatologistPharmacyHours> dermatologistsActiveHours = getDermatologistActiveWorkingHours(dermatologistId);
				
		for(DermatologistPharmacyHours h : dermatologistsActiveHours) {
			if(h.getPharmacy().getId() == pharmacyId && !h.isDeleted()) {
				h.setDeleted(true);
				DermatologistPharmacyHours hours = repository.findOneById(h.getId());
				repository.save(hours);
				return hours;
			}
		}
				
		return null;
	}
}
