package com.example.pharmacySystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.pharmacySystem.model.Authority;
import com.example.pharmacySystem.model.PharmacySystemAdministrator;
import com.example.pharmacySystem.model.User;
import com.example.pharmacySystem.repository.PharmacySystemAdministratorRepository;
import com.example.pharmacySystem.repository.UserRepository;

@Service
public class PharmacySystemAdministratorService {

	@Autowired
	private PharmacySystemAdministratorRepository systemAdministratorRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthorityService authorityService;
	
	public List<PharmacySystemAdministrator> findAll(){
		return systemAdministratorRepository.findAll();
	}
	
	public PharmacySystemAdministrator findOneById(Long id) {
		return systemAdministratorRepository.findOneById(id);
	}
	
	public PharmacySystemAdministrator findOneByUserId(Long id) {
		return systemAdministratorRepository.findOneByUserId(id);
	}
	
	public PharmacySystemAdministrator updateSystemAdministrator(PharmacySystemAdministrator admin) {
		PharmacySystemAdministrator myAdmin = systemAdministratorRepository.findOneById(admin.getId());
		myAdmin.getUser().setFirstName(admin.getUser().getFirstName());
		myAdmin.getUser().setLastName(admin.getUser().getLastName());
		systemAdministratorRepository.save(myAdmin);
		return myAdmin;
	}
	
	public PharmacySystemAdministrator create(PharmacySystemAdministrator admin) {
		User myUser = userRepository.findOneByEmail(admin.getUser().getEmail());
		
		if(myUser != null) return null;
		
		PharmacySystemAdministrator newAdmin = new PharmacySystemAdministrator();
		
		myUser = new User();
		myUser.setEmail(admin.getUser().getEmail());
		myUser.setPassword(passwordEncoder.encode(admin.getUser().getPassword()));
		myUser.setFirstName(admin.getUser().getFirstName());
		myUser.setLastName(admin.getUser().getLastName());
		myUser.setActivated(true);
		myUser.setEnabled(true);
		myUser.setFirstLogin(true);
		myUser.setType("ROLE_PHARMACY_SYSTEM_ADMIN");
		List<Authority> authorities = authorityService.findByName("ROLE_PHARMACY_SYSTEM_ADMIN");
		myUser.setAuthorities(authorities);
		newAdmin.setUser(myUser);
		
		return systemAdministratorRepository.save(newAdmin);
	}
}
