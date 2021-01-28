package com.example.pharmacySystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.pharmacySystem.model.Authority;
import com.example.pharmacySystem.model.Patient;
import com.example.pharmacySystem.model.User;
import com.example.pharmacySystem.repository.PatientRepository;
import com.example.pharmacySystem.repository.UserRepository;

@Service
public class LoginService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String email) {
		User user = userRepository.findOneByEmail(email);
		return user;
	}
	
	public User login(User user) {
		User myUser = (User) loadUserByUsername(user.getEmail());
		
		if(myUser == null) return null;
		
		if(myUser != null && myUser.getType().equals("ROLE_PATIENT"))
			return myUser;
		else if(!myUser.isFirstLogin() && !myUser.getType().equals("ROLE_PATIENT"))
			return myUser;
		else if(!myUser.getType().equals("ROLE_PATIENT"))
			return myUser;
		else
			return null;
	}
	
	public Patient register(Patient patient) {
		User myUser = (User) loadUserByUsername(patient.getUser().getEmail());
		
		if(myUser != null)
			return null;
		
		patient.getUser().setPassword(passwordEncoder.encode(patient.getUser().getPassword()));
		patient.getUser().setEnabled(true);
		patient.getUser().setActivated(false);
		patient.getUser().setType("ROLE_PATIENT");
		List<Authority> authorities = authorityService.findByName("ROLE_PATIENT");
		patient.getUser().setAuthorities(authorities);
		patientRepository.save(patient);
		
		return patient;
	}
	
	public String getUserRole(String email) {
		User myUser = userRepository.findOneByEmail(email);
		
		if(myUser == null) return "NO_USER_ROLE";
		
		return myUser.getType();
	}

}
