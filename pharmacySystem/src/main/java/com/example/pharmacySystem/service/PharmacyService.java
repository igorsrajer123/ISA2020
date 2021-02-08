package com.example.pharmacySystem.service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.pharmacySystem.dto.PharmacyDto;
import com.example.pharmacySystem.model.Counseling;
import com.example.pharmacySystem.model.Dermatologist;
import com.example.pharmacySystem.model.Examination;
import com.example.pharmacySystem.model.Pharmacist;
import com.example.pharmacySystem.model.Pharmacy;
import com.example.pharmacySystem.model.PharmacyAdministrator;
import com.example.pharmacySystem.repository.DermatologistRepository;
import com.example.pharmacySystem.repository.ExaminationRepository;
import com.example.pharmacySystem.repository.PharmacistRepository;
import com.example.pharmacySystem.repository.PharmacyAdministratorRepository;
import com.example.pharmacySystem.repository.PharmacyRepository;

@Service
@Transactional(readOnly = true)
public class PharmacyService {

	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private PharmacyAdministratorRepository pharmacyAdminRepository;
	
	@Autowired
	private DermatologistRepository dermatologistRepository;
	
	@Autowired
	private PharmacistRepository pharmacistRepository;
	
	@Autowired
	private ExaminationRepository examinationRepository;
	
	public List<Pharmacy> findAll(){
		List<Pharmacy> allPharmacies = pharmacyRepository.findAll();
		return allPharmacies;
	}
	
	public Pharmacy findOneById(Long id) {
		Pharmacy pharmacy = pharmacyRepository.findOneById(id);	
		return pharmacy;
	}
	
	public Pharmacy findOneByName(String name) {
		Pharmacy pharmacy = pharmacyRepository.findOneByName(name);
		return pharmacy;
	}
	
	@Transactional(readOnly = false)
	public Pharmacy create(PharmacyDto pharmacy) {
		Pharmacy newPharmacy = new Pharmacy();
		newPharmacy.setName(pharmacy.getName());
		newPharmacy.setAddress(pharmacy.getAddress());
		newPharmacy.setCity(pharmacy.getCity());
		newPharmacy.setCounselingPrice(pharmacy.getCounselingPrice());
		pharmacyRepository.save(newPharmacy);
		return newPharmacy;
	}
	
	public Pharmacy getAdminsPharmacy(Long id) {
		PharmacyAdministrator myAdmin = pharmacyAdminRepository.findOneById(id);	
		Pharmacy adminsPharmacy = myAdmin.getPharmacy();
		return adminsPharmacy;
	}
	
	@Transactional(readOnly = false)
	public Pharmacy updatePharmacy(PharmacyDto pharmacyDto) {
		Pharmacy myPharmacy = pharmacyRepository.findOneById(pharmacyDto.getId());
		myPharmacy.setName(pharmacyDto.getName());
		myPharmacy.setAddress(pharmacyDto.getAddress());
		myPharmacy.setCity(pharmacyDto.getCity());
		myPharmacy.setDescription(pharmacyDto.getDescription());
		myPharmacy.setCounselingPrice(pharmacyDto.getCounselingPrice());
		pharmacyRepository.save(myPharmacy);
		System.out.println(myPharmacy.getName());
		return myPharmacy;
	}
	
	public List<Pharmacy> getDermatologistPharmacies(Long dermatologistId){
		Dermatologist dermatologist = dermatologistRepository.findOneById(dermatologistId);
		List<Pharmacy> pharmacies = dermatologist.getPharmacies();
		return pharmacies;
	}
	
	public Pharmacy getPharmacistPharmacy(Long pharmacistId){
		Pharmacist pharmacist = pharmacistRepository.findOneById(pharmacistId);
		Pharmacy pharmacy = pharmacist.getPharmacy();
		return pharmacy;
	}
	
	public Pharmacy getPharmacyByExaminationId(Long examinationId) {
		Examination ex = examinationRepository.findOneById(examinationId);
		
		Long pharmacyId = ex.getPharmacy().getId();
		
		Pharmacy p = pharmacyRepository.findOneById(pharmacyId);
		return p;
	}
	
	public List<Pharmacy> getPharmaciesWithAvailablePharmacists(String preferencedTime, String date){
		List<Pharmacy> allPharmacies = pharmacyRepository.findAll();
		List<Pharmacy> retVal = new ArrayList<Pharmacy>();
		
		int time = Integer.parseInt(preferencedTime);
		LocalDate d1 = LocalDate.parse(date);
		Date ourDate = java.sql.Date.valueOf(d1);
		
		for(Pharmacy p : allPharmacies) {
			if(p.getPharmacists().size() > 0) {
				for(Pharmacist pharmacist : p.getPharmacists()) {
					if(!pharmacist.isDeleted()) {
						if(time >= pharmacist.getFrom() && time <= pharmacist.getTo()) {
							System.out.println("Termin upada!!!");
							List<Counseling> pharmacistCounselings = pharmacist.getCounselings();
							int flag = 0;
							for(Counseling counseling : pharmacistCounselings) {
								LocalDate cDate = counseling.getDate();
								Date counselingDate = java.sql.Date.valueOf(cDate);
								
								if(ourDate.equals(counselingDate)) {
									if(time == counseling.getFrom() || (time < counseling.getTo() && time > counseling.getFrom())) {
										if(counseling.getStatus().equals("ACTIVE")) {
											flag++;
											System.out.println(flag);
											continue;
										}
									}
								}
							}
							
							if(flag == 0) 
								retVal.add(p);	
						}
					}
				}
			}
		}
		retVal = retVal.stream().distinct().collect(Collectors.toList());
		return retVal;
	}
}
