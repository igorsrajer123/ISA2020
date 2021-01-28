package com.example.pharmacySystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pharmacySystem.model.Authority;
import com.example.pharmacySystem.repository.AuthorityRepository;

@Service
public class AuthorityService {

	@Autowired
	private AuthorityRepository authorityRepository;
	
	public List<Authority> findByName(String name){
		List<Authority> authorities = new ArrayList<>();
		Authority myAuthority = authorityRepository.findOneByName(name);
		authorities.add(myAuthority);
		
		return authorities;
	}
}
