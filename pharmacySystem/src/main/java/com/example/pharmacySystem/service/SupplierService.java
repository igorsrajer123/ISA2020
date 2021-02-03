package com.example.pharmacySystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.pharmacySystem.model.Authority;
import com.example.pharmacySystem.model.Supplier;
import com.example.pharmacySystem.model.User;
import com.example.pharmacySystem.repository.SupplierRepository;
import com.example.pharmacySystem.repository.UserRepository;

@Service
public class SupplierService {

	@Autowired
	private SupplierRepository supplierRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthorityService authorityService;
	
	public List<Supplier> findAll(){
		return supplierRepository.findAll();
	}
	
	public Supplier findOneById(Long id) {
		return supplierRepository.findOneById(id);
	}
	
	public Supplier create(Supplier supplier) {
		User user = userRepository.findOneByEmail(supplier.getUser().getEmail());
		
		if(user != null) return null;
		
		Supplier newSupplier = new Supplier();
		user = new User();
		user.setEmail(supplier.getUser().getEmail());
		user.setPassword(passwordEncoder.encode(supplier.getUser().getPassword()));
		user.setFirstName(supplier.getUser().getFirstName());
		user.setLastName(supplier.getUser().getLastName());
		user.setActivated(true);
		user.setEnabled(true);
		user.setFirstLogin(true);
		user.setType("ROLE_SUPPLIER");
		List<Authority> authorities = authorityService.findByName("ROLE_SUPPLIER");
		user.setAuthorities(authorities);
		newSupplier.setUser(user);

		return supplierRepository.save(newSupplier);
	}
}
