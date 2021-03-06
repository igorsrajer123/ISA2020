package com.example.pharmacySystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.pharmacySystem.model.Absence;
import com.example.pharmacySystem.model.Authority;
import com.example.pharmacySystem.model.Dermatologist;
import com.example.pharmacySystem.model.Examination;
import com.example.pharmacySystem.model.Pharmacy;
import com.example.pharmacySystem.model.User;
import com.example.pharmacySystem.repository.AbsenceRepository;
import com.example.pharmacySystem.repository.DermatologistRepository;
import com.example.pharmacySystem.repository.ExaminationRepository;
import com.example.pharmacySystem.repository.PharmacyRepository;
import com.example.pharmacySystem.repository.UserRepository;

@Service
public class DermatologistService {

	@Autowired
	private DermatologistRepository dermatologistRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private ExaminationRepository examinationRepository;
	
	@Autowired
	private AbsenceRepository absenceRepository;
	
	public List<Dermatologist> findAll(){
		return dermatologistRepository.findAll();
	}
	
	public Dermatologist findOneById(Long id) {
		return dermatologistRepository.findOneById(id);
	}
	
	@Transactional(readOnly = false)
	public Dermatologist Create(Dermatologist dermatologist) {
		User user = userRepository.findOneByEmail(dermatologist.getUser().getEmail());
		
		if(user != null) return null;
		
		Dermatologist newDermatologist = new Dermatologist();
		
		user = new User();
		user.setEmail(dermatologist.getUser().getEmail());
		user.setPassword(passwordEncoder.encode(dermatologist.getUser().getPassword()));
		user.setFirstName(dermatologist.getUser().getFirstName());
		user.setLastName(dermatologist.getUser().getLastName());
		user.setActivated(true);
		user.setEnabled(true);
		user.setFirstLogin(true);
		user.setType("ROLE_DERMATOLOGIST");
		List<Authority> authorities = authorityService.findByName("ROLE_DERMATOLOGIST");
		user.setAuthorities(authorities);
		newDermatologist.setUser(user);
		newDermatologist.setRating(0);
		newDermatologist.setNumberOfVotes(0);
		
		return dermatologistRepository.save(newDermatologist);
	}
	
	public List<Dermatologist> getPharmacyDermatologists(Long pharmacyId){
		Pharmacy myPharmacy = pharmacyRepository.findOneById(pharmacyId);
		List<Dermatologist> dermatologists = myPharmacy.getDermatologists();
		return dermatologists;
	}
	
	public List<Dermatologist> getDermatologistsNotInPharmacy(Long pharmacyId){
		Pharmacy myPharmacy = pharmacyRepository.findOneById(pharmacyId);
		List<Dermatologist> dermatologists = myPharmacy.getDermatologists();
		List<Dermatologist> allDermatologists = dermatologistRepository.findAll();
		allDermatologists.removeAll(dermatologists);
		return allDermatologists;
	}

	public Dermatologist addDermatologistToPharmacy(Long dermatologistId, Long pharmacyId) {
		Dermatologist dermatologist = dermatologistRepository.findOneById(dermatologistId);
		Pharmacy pharmacy = pharmacyRepository.findOneById(pharmacyId);		
		dermatologist.getPharmacies().add(pharmacy);
		pharmacy.getDermatologists().add(dermatologist);
		dermatologistRepository.save(dermatologist);
		
		return dermatologist;
	}

	public Dermatologist removeDermatologistFromPharmacy(Long dermatologistId, Long pharmacyId) {
		Dermatologist dermatologist = dermatologistRepository.findOneById(dermatologistId);
		Pharmacy pharmacy = pharmacyRepository.findOneById(pharmacyId);
		
		List<Examination> dermatologistExaminations = examinationRepository.findAllByStatusAndDermatologistIdAndPharmacyId("ACTIVE", dermatologistId, pharmacyId);
		if(dermatologistExaminations.size() > 0) {
			Dermatologist d = new Dermatologist();
			d.setId(Long.valueOf(-1));
			return d;
		}else {
			
			//uklanjamo sve preglede koje dermatolog ima za tu konkretnu apoteku
			List<Examination> dermatologistExaminations2 = dermatologist.getExaminations();
			Examination examination3 = null;
			for(Examination exam : dermatologistExaminations2) {
				if(exam.getPharmacy().getId() == pharmacyId) {
					//dermatologist.getExaminations().remove(exam);
					examination3 = exam;
					Examination examination = examinationRepository.findOneById(exam.getId());
					examination.setStatus("DELETED");
				}
			}
			System.out.println(examination3);
			dermatologist.getExaminations().remove(examination3);
			dermatologist.getPharmacies().remove(pharmacy);
			pharmacy.getDermatologists().remove(dermatologist);
			dermatologistRepository.save(dermatologist);
		}
		
		return dermatologist;
	}
	
	public Dermatologist getDermatologistByExaminationId(Long examinationId) {
		Examination ex = examinationRepository.findOneById(examinationId);
		
		Long dermatolistId = ex.getDermatologist().getId();
		Dermatologist dermatologist = dermatologistRepository.findOneById(dermatolistId);
		
		return dermatologist;
	}
	
	public Dermatologist getDermatologistFromAbsence(Long absenceId) {
		Absence a = absenceRepository.findOneById(absenceId);
		Dermatologist d = dermatologistRepository.findOneById(a.getDermatologist().getId());
		return d;
	}
}
