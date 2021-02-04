package com.example.pharmacySystem.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.pharmacySystem.dto.PharmacyDto;
import com.example.pharmacySystem.model.Dermatologist;
import com.example.pharmacySystem.model.Pharmacist;
import com.example.pharmacySystem.model.Pharmacy;
import com.example.pharmacySystem.model.PharmacyAdministrator;
import com.example.pharmacySystem.repository.DermatologistRepository;
import com.example.pharmacySystem.repository.PharmacistRepository;
import com.example.pharmacySystem.repository.PharmacyAdministratorRepository;
import com.example.pharmacySystem.repository.PharmacyRepository;

@Service
@Transactional(readOnly = true)
public class PharmacyService {

	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private PharmacyAdministratorRepository pharmacyAdminRepository;
	
	@Autowired
	private DermatologistRepository dermatologistRepository;
	
	@Autowired
	private PharmacistRepository pharmacistRepository;
	
	public List<Pharmacy> findAll(){
		List<Pharmacy> allPharmacies = pharmacyRepository.findAll();
		return allPharmacies;
	}
	
	public Pharmacy findOneById(Long id) {
		Pharmacy pharmacy = pharmacyRepository.findOneById(id);	
		return pharmacy;
	}
	
	public Pharmacy findOneByName(String name) {
		Pharmacy pharmacy = pharmacyRepository.findOneByName(name);
		return pharmacy;
	}
	
	@Transactional(readOnly = false)
	public Pharmacy create(PharmacyDto pharmacy) {
		Pharmacy newPharmacy = new Pharmacy();
		newPharmacy.setName(pharmacy.getName());
		newPharmacy.setAddress(pharmacy.getAddress());
		newPharmacy.setCity(pharmacy.getCity());
		pharmacyRepository.save(newPharmacy);
		return newPharmacy;
	}
	
	public Pharmacy getAdminsPharmacy(Long id) {
		PharmacyAdministrator myAdmin = pharmacyAdminRepository.findOneById(id);	
		Pharmacy adminsPharmacy = myAdmin.getPharmacy();
		return adminsPharmacy;
	}
	
	@Transactional(readOnly = false)
	public Pharmacy updatePharmacy(PharmacyDto pharmacyDto) {
		Pharmacy myPharmacy = pharmacyRepository.findOneById(pharmacyDto.getId());
		myPharmacy.setName(pharmacyDto.getName());
		myPharmacy.setAddress(pharmacyDto.getAddress());
		myPharmacy.setCity(pharmacyDto.getCity());
		myPharmacy.setDescription(pharmacyDto.getDescription());
		pharmacyRepository.save(myPharmacy);
		System.out.println(myPharmacy.getName());
		return myPharmacy;
	}
	
	public List<Pharmacy> getDermatologistPharmacies(Long dermatologistId){
		Dermatologist dermatologist = dermatologistRepository.findOneById(dermatologistId);
		List<Pharmacy> pharmacies = dermatologist.getPharmacies();
		return pharmacies;
	}
	
	public Pharmacy getPharmacistPharmacy(Long pharmacistId){
		Pharmacist pharmacist = pharmacistRepository.findOneById(pharmacistId);
		Pharmacy pharmacy = pharmacist.getPharmacy();
		return pharmacy;
	}
}
