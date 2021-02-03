package com.example.pharmacySystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.pharmacySystem.model.Authority;
import com.example.pharmacySystem.model.Dermatologist;
import com.example.pharmacySystem.model.User;
import com.example.pharmacySystem.repository.DermatologistRepository;
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
	
	public List<Dermatologist> findAll(){
		return dermatologistRepository.findAll();
	}
	
	public Dermatologist findOneById(Long id) {
		return dermatologistRepository.findOneById(id);
	}
	
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
		
		return dermatologistRepository.save(newDermatologist);
	}
}
