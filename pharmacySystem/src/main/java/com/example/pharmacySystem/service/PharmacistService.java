package com.example.pharmacySystem.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.pharmacySystem.model.Authority;
import com.example.pharmacySystem.model.Counseling;
import com.example.pharmacySystem.model.Pharmacist;
import com.example.pharmacySystem.model.Pharmacy;
import com.example.pharmacySystem.model.User;
import com.example.pharmacySystem.repository.CounselingRepository;
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
	private CounselingRepository counselingRepository;
	
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
		
		if(pharmacist.getCounselings().size() > 0)
			for(Counseling c : pharmacist.getCounselings()) {
				if(c.getStatus().equals("ACTIVE")) {
					System.out.println("Postoji aktivan!");
					return null;
				}
			}
		
		for(Counseling c : pharmacist.getCounselings()) {
			c.setStatus("DONE");
		}
		
		User user = pharmacist.getUser();
		user.setEnabled(false);
		pharmacist.setDeleted(true);
		pharmacy.getPharmacists().remove(pharmacist);
		pharmacistRepository.save(pharmacist);
		
		return pharmacist;
	}
	
	public List<Pharmacist> getAvailablePharmacists(Long pharmacyId, String preferencedTime, String date){
		Pharmacy pharmacy = pharmacyRepository.findOneById(pharmacyId);
		List<Pharmacist> pharmacyPharmacists = pharmacy.getPharmacists();
		List<Pharmacist> retVal = new ArrayList<Pharmacist>();
		
		int time = Integer.parseInt(preferencedTime);
		LocalDate d1 = LocalDate.parse(date);
		Date ourDate = java.sql.Date.valueOf(d1);
		
		if(pharmacyPharmacists.size() > 0) {
			for(Pharmacist p : pharmacyPharmacists) {
				if(!p.isDeleted()) {
					if(time >= p.getFrom() && time <= p.getTo()) {			
						int flag = 0;			
						for(Counseling counseling : p.getCounselings()) {		
							if(ourDate.equals(java.sql.Date.valueOf(counseling.getDate()))) {
								if(time == counseling.getFrom() || (time < counseling.getTo() && time > counseling.getFrom())) {					
									if(counseling.getStatus().equals("ACTIVE")) {
										System.out.println("NASLI AKTIVAN TERMIN U NASE VREME");
										flag++;
										System.out.println(flag);
										continue;
									}
								}
							}
						}
						System.out.println(flag + "******");
						if(flag == 0)
							retVal.add(p);			
					}
				}
			}
		}
		
		
		retVal = retVal.stream().distinct().collect(Collectors.toList());
		return retVal;
	}
	
	public Pharmacist getPharmacistFromCounseling(Long counselingId) {
		Counseling c = counselingRepository.findOneById(counselingId);
		Pharmacist p = pharmacistRepository.findOneById(c.getPharmacist().getId());
		return p;
	}
}
