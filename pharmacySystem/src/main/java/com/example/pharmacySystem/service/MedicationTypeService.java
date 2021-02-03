package com.example.pharmacySystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pharmacySystem.model.MedicationType;
import com.example.pharmacySystem.repository.MedicationTypeRepository;

@Service
public class MedicationTypeService {

	@Autowired
	private MedicationTypeRepository typeRepository;
	
	public List<MedicationType> findAll(){
		return typeRepository.findAll();
	}
	
	public MedicationType findOneById(Long id) {
		return typeRepository.findOneById(id);
	}
	
	public MedicationType findOneByName(String name) {
		return typeRepository.findOneByName(name);
	}
}
