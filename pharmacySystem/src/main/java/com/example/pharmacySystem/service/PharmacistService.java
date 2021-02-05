package com.example.pharmacySystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.pharmacySystem.model.Authority;
import com.example.pharmacySystem.model.Pharmacist;
import com.example.pharmacySystem.model.Pharmacy;
import com.example.pharmacySystem.model.User;
import com.example.pharmacySystem.repository.PharmacistRepository;
import com.example.pharmacySystem.repository.PharmacyRepository;
import com.example.pharmacySystem.repository.UserRepository;

@Service
public class PharmacistService {

	@Autowired
	private PharmacistRepository pharmacistRepository;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthorityService authorityService;
	
	public List<Pharmacist> findAll(){
		return pharmacistRepository.findAll();
	}
	
	public Pharmacist findOneById(Long id) {
		return pharmacistRepository.findOneById(id);
	}
	
	public List<Pharmacist> getPharmacyActivePharmacists(Long pharmacyId){
		Pharmacy pharmacy = pharmacyRepository.findOneById(pharmacyId);
		
		List<Pharmacist> pharmacists = pharmacy.getPharmacists();
		List<Pharmacist> activePharmacists = new ArrayList<Pharmacist>();
		for(Pharmacist p : pharmacists)
			if(!p.isDeleted())
				activePharmacists.add(p);
		
		return activePharmacists;
	}
	
	public Pharmacist create(Pharmacist pharmacist) {
		User user = userRepository.findOneByEmail(pharmacist.getUser().getEmail());
		
		if(user != null) return null;
		
		Pharmacist newPharmacist = new Pharmacist();
		user = new User();
		user.setEmail(pharmacist.getUser().getEmail());
		user.setPassword(passwordEncoder.encode(pharmacist.getUser().getPassword()));
		user.setFirstName(pharmacist.getUser().getFirstName());
		user.setLastName(pharmacist.getUser().getLastName());
		user.setActivated(true);
		user.setEnabled(true);
		user.setFirstLogin(true);
		user.setType("ROLE_PHARMACIST");
		List<Authority> authorities = authorityService.findByName("ROLE_PHARMACIST");
		user.setAuthorities(authorities);
		newPharmacist.setUser(user);
		newPharmacist.setDeleted(false);
		newPharmacist.setRating(0);
		newPharmacist.setNumberOfVotes(0);
		newPharmacist.setFrom(pharmacist.getFrom());
		newPharmacist.setTo(pharmacist.getTo());
		newPharmacist.setPharmacy(pharmacyRepository.findOneById(pharmacist.getPharmacy().getId()));
		
		return pharmacistRepository.save(newPharmacist);
	}
	
	public Pharmacist removePharmacistFromPharmacy(Long pharmacistId, Long pharmacyId) {
		Pharmacist pharmacist = pharmacistRepository.findOneById(pharmacistId);
		Pharmacy pharmacy = pharmacyRepository.findOneById(pharmacyId);
		User user = pharmacist.getUser();
		user.setEnabled(false);
		pharmacist.setDeleted(true);
		pharmacy.getPharmacists().remove(pharmacist);
		pharmacistRepository.save(pharmacist);
		
		return pharmacist;
	}
}
