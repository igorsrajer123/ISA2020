package com.example.pharmacySystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.pharmacySystem.dto.PharmacyAdministratorDto;
import com.example.pharmacySystem.model.Authority;
import com.example.pharmacySystem.model.PharmacyAdministrator;
import com.example.pharmacySystem.model.User;
import com.example.pharmacySystem.repository.PharmacyAdministratorRepository;
import com.example.pharmacySystem.repository.UserRepository;

@Service
public class PharmacyAdministratorService {

	@Autowired
	private PharmacyAdministratorRepository pharmacyAdminRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private PharmacyService pharmacyService;
	
	public List<PharmacyAdministrator> findAll(){
		return pharmacyAdminRepository.findAll();
	}
	
	public PharmacyAdministrator findOneById(Long id) {
		return pharmacyAdminRepository.findOneById(id);
	}
	
	public PharmacyAdministrator create(PharmacyAdministrator admin){
		User user = userRepository.findOneByEmail(admin.getUser().getEmail());
		
		if(user != null) return null;
		
		PharmacyAdministrator newAdmin = new PharmacyAdministrator();
		user = new User();
		user.setEmail(admin.getUser().getEmail());
		user.setPassword(passwordEncoder.encode(admin.getUser().getPassword()));
		user.setFirstName(admin.getUser().getFirstName());
		user.setLastName(admin.getUser().getLastName());
		user.setActivated(true);
		user.setEnabled(true);
		user.setFirstLogin(true);
		user.setType("ROLE_PHARMACY_ADMIN");
		List<Authority> authorities = authorityService.findByName("ROLE_PHARMACY_ADMIN");
		user.setAuthorities(authorities);
		newAdmin.setUser(user);
		newAdmin.setPharmacy(pharmacyService.findOneById(admin.getPharmacy().getId()));
		
		return pharmacyAdminRepository.save(newAdmin);
	}
	
	public PharmacyAdministrator updateAdmin(PharmacyAdministratorDto admin) {
		PharmacyAdministrator myAdmin = pharmacyAdminRepository.findOneById(admin.getId());
		myAdmin.getUser().setFirstName(admin.getUser().getFirstName());
		myAdmin.getUser().setLastName(admin.getUser().getLastName());
		pharmacyAdminRepository.save(myAdmin);		
		return myAdmin;
	}
}
