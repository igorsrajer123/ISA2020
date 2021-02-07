package com.example.pharmacySystem.service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.pharmacySystem.model.Dermatologist;
import com.example.pharmacySystem.model.DermatologistPharmacyHours;
import com.example.pharmacySystem.model.Examination;
import com.example.pharmacySystem.model.Patient;
import com.example.pharmacySystem.model.Pharmacy;
import com.example.pharmacySystem.repository.DermatologistPharmacyHoursRepository;
import com.example.pharmacySystem.repository.DermatologistRepository;
import com.example.pharmacySystem.repository.ExaminationRepository;
import com.example.pharmacySystem.repository.PatientRepository;
import com.example.pharmacySystem.repository.PharmacyRepository;

@Service
public class ExaminationService {

	@Autowired
	private ExaminationRepository examinationRepository;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private DermatologistRepository dermatologistRepository;
	
	@Autowired
	private DermatologistPharmacyHoursRepository dpRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	public List<Examination> findAll(){
		return examinationRepository.findAll();
	}
	
	public Examination findOneById(Long id) {
		return examinationRepository.findOneById(id);
	}
	
	public List<Examination> findAllByDermatologistId(Long id){
		return examinationRepository.findAllByDermatologistId(id);
	}
	
	public List<Examination> findAllActiveAndFreeExaminationsForDermatologistInPharmacy(Long dermatologistId, Long pharmacyId){
		List<Examination> dermatologistExaminations = findAllByDermatologistId(dermatologistId);
		List<Examination> retVal = new ArrayList<Examination>();
		
		for(Examination e : dermatologistExaminations) {
			if(e.getPharmacy().getId().equals(pharmacyId)) {
				if(e.getStatus().equals("FREE") || e.getStatus().equals("ACTIVE")) {
					retVal.add(e);
				}
			}
		}
		
		return retVal;
	}
	
	//**examination can start only at full hour (08:00, 09:00, ...)
	public Examination createExamination(Examination e) {
		Dermatologist d = dermatologistRepository.findOneById(e.getDermatologist().getId());
		Pharmacy pharmacy = pharmacyRepository.findOneById(e.getPharmacy().getId());
		
		LocalDate newExaminationDate = e.getDate();
		String newExaminationTime = e.getTime();
		
		String[] parts = newExaminationTime.split(":");
		String newExaminationStartTime = parts[0];
		double newExaminationStartTimeDouble = Double.parseDouble(newExaminationStartTime);
		double newExaminationDuration = e.getDuration();
		double newExaminationEndTime = newExaminationStartTimeDouble + newExaminationDuration;
		
		//hour from 
		int newExaminationStartTimeInt = (int) newExaminationStartTimeDouble;
		
		//hour to
		int newExaminationEndTimeHoursInt = (int) newExaminationEndTime;
		
		double newExaminationEndTimeMinutesDouble = (10 * newExaminationEndTime - 10 * newExaminationEndTimeHoursInt)/10;
		
		//minutes to
		int newExaminationEndTimeMinutesInt = 0;
		if(newExaminationEndTimeMinutesDouble == 0.5)
			newExaminationEndTimeMinutesInt = 30;
		//----------------------------------------------------------------------------
		Date newExaminationDateDay = java.sql.Date.valueOf(newExaminationDate);
		
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(newExaminationDateDay);
		calendar1.set(Calendar.HOUR_OF_DAY, newExaminationStartTimeInt);
		calendar1.set(Calendar.MINUTE, 0);
		calendar1.set(Calendar.SECOND, 0);
		calendar1.set(Calendar.MILLISECOND, 0);
		Date date1 = calendar1.getTime();
		//----------------------------------------------------------------------------		
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(newExaminationDateDay);
		calendar2.set(Calendar.HOUR_OF_DAY, newExaminationEndTimeHoursInt);
		calendar2.set(Calendar.MINUTE, newExaminationEndTimeMinutesInt);
		calendar2.set(Calendar.SECOND, 0);
		calendar2.set(Calendar.MILLISECOND, 0);
		Date date2 = calendar2.getTime();
		//----------------------------------------------------------------------------
		DermatologistPharmacyHours dp = dpRepository.findOneByDermatologistIdAndPharmacyIdAndDeleted(d.getId(), pharmacy.getId(), false);
		int worksFrom = dp.getFrom();
		int worksTo = dp.getTo();
		
		//dermatologist working hours at pharmacy
		Calendar calendar3 = Calendar.getInstance();
		calendar3.setTime(newExaminationDateDay);
		calendar3.set(Calendar.HOUR_OF_DAY, worksFrom);
		calendar3.set(Calendar.MINUTE, 0);
		calendar3.set(Calendar.SECOND, 0);
		calendar3.set(Calendar.MILLISECOND, 0);
		Date date3 = calendar3.getTime();
		//-----------------------------------------------------------------------------		
		Calendar calendar4 = Calendar.getInstance();
		calendar4.setTime(newExaminationDateDay);
		calendar4.set(Calendar.HOUR_OF_DAY, worksTo);
		calendar4.set(Calendar.MINUTE, 0);
		calendar4.set(Calendar.SECOND, 0);
		calendar4.set(Calendar.MILLISECOND, 0);
		Date date4 = calendar4.getTime();
		//-----------------------------------------------------------------------------

		//check if they overlapse
		if(date1.equals(date3) || date1.after(date3)) {
			if(date1.before(date4)){
				if(date2.before(date4) || date2.equals(date4)){
					System.out.println("Doslo je do preklapanja sa radnim vremenom!");
					
					List<Examination> activeAndFreeExaminations = findAllActiveAndFreeExaminationsForDermatologistInPharmacy(d.getId(), pharmacy.getId());
					for(Examination ex : activeAndFreeExaminations) {
						System.out.println("Usli u petlju!");
						String oldExaminationTime = ex.getTime();	
						String[] parts2 = oldExaminationTime.split(":");
						String oldExaminationStartTime = parts2[0];
						double oldExaminationStartTimeDouble = Double.parseDouble(oldExaminationStartTime);
						double oldExaminationDuration = ex.getDuration();
						double oldExaminationEndTime = oldExaminationStartTimeDouble + oldExaminationDuration;
						
						//hour from 
						int oldExaminationStartTimeInt = (int) oldExaminationStartTimeDouble;
						
						//hour to
						int oldExaminationEndTimeHoursInt = (int) oldExaminationEndTime;
						
						double oldExaminationEndTimeMinutesDouble = (10 * oldExaminationEndTime - 10 * oldExaminationEndTimeHoursInt)/10;
						//minutes to
						int oldExaminationEndTimeMinutesInt = 0;
						if(oldExaminationEndTimeMinutesDouble == 0.5)
							oldExaminationEndTimeMinutesInt = 30;
						
						//----------------------------------------------------------------------------
						LocalDate oldExaminationDate = ex.getDate();
						Date oldExaminationDateDay = java.sql.Date.valueOf(oldExaminationDate);
						
						Calendar calendar5 = Calendar.getInstance();
						calendar5.setTime(oldExaminationDateDay);
						calendar5.set(Calendar.HOUR_OF_DAY, oldExaminationStartTimeInt);
						calendar5.set(Calendar.MINUTE, 0);
						calendar5.set(Calendar.SECOND, 0);
						calendar5.set(Calendar.MILLISECOND, 0);
						Date date5 = calendar5.getTime();
						//----------------------------------------------------------------------------		
						Calendar calendar6 = Calendar.getInstance();
						calendar6.setTime(oldExaminationDateDay);
						calendar6.set(Calendar.HOUR_OF_DAY, oldExaminationEndTimeHoursInt);
						calendar6.set(Calendar.MINUTE, oldExaminationEndTimeMinutesInt);
						calendar6.set(Calendar.SECOND, 0);
						calendar6.set(Calendar.MILLISECOND, 0);
						Date date6 = calendar6.getTime();
						
						if(date1.before(date6) && date5.before(date2)) {
							System.out.println("NADJENO PREKLAPANJE, VRATI GRESKU!");
							Examination newEx = new Examination();
							newEx.setId(Long.valueOf(-1));
							return newEx;
						}
					}
					
					Examination ex = new Examination();
					ex.setDate(e.getDate());
					ex.setDuration(e.getDuration());
					ex.setTime(e.getTime());
					ex.setStatus(e.getStatus());
					ex.setPrice(e.getPrice());
					ex.setDermatologist(dermatologistRepository.findOneById(e.getDermatologist().getId()));
					ex.setPharmacy(pharmacyRepository.findOneById(e.getPharmacy().getId()));
					ex.setPatient(null);
					examinationRepository.save(ex);
					System.out.println("Kreiran examination!");
					return ex;					
				}
			}
		}
		
		System.out.println("izvan radnog vremena!");
		return null;
	}
	
	public List<Examination> findAllByStatusAndDermatologistIdAndPharmacyId(String status, Long dermatologistId, Long pharmacyId){
		return examinationRepository.findAllByStatusAndDermatologistIdAndPharmacyId(status, dermatologistId, pharmacyId);
	}	
	
	public List<Examination> findAllByDermatologistIdAndPharmacyId(Long dermatologistId, Long pharmacyId){
		return examinationRepository.findAllByDermatologistIdAndPharmacyId(dermatologistId, pharmacyId);
	}
	
	public List<Examination> findAllByStatusAndDermatologistId(String status, Long dermatologistId){
		return examinationRepository.findAllByStatusAndDermatologistId(status, dermatologistId);
	}
	
	public List<Examination> findAllByPharmacyId(Long pharmacyId){
		return examinationRepository.findAllByPharmacyId(pharmacyId);
	}
	
	public List<Examination> findAllByPharmacyIdAndStatus(Long pharamcyId, String status){
		return examinationRepository.findAllByPharmacyIdAndStatus(pharamcyId, status);
	}
	
	public Examination reserveExaminationByPatient(Long examinationId, Long patientId) {
		Examination ex = examinationRepository.findOneById(examinationId);
		Patient p = patientRepository.findOneById(patientId);
		
		ex.setStatus("ACTIVE");
		ex.setPatient(p);
		p.getExaminations().add(ex);
		examinationRepository.save(ex);
		return ex;
	}
	
	public Examination cancellPatientExamination(Long patientId, Long examinationId) {
		Examination ex = examinationRepository.findOneById(examinationId);
		
		LocalDate examinationStartDate = ex.getDate();
		String examinationStartTime = ex.getTime();
		
		String[] parts = examinationStartTime.split(":");
		String examinationStartHour = parts[0];
		double examinationStartHourDouble = Double.parseDouble(examinationStartHour);
		int examinationStartHourInt = (int) examinationStartHourDouble;
		
		Date examinationDate = java.sql.Date.valueOf(examinationStartDate);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(examinationDate);
		calendar.set(Calendar.HOUR_OF_DAY, examinationStartHourInt);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date date1 = calendar.getTime();
		long MILLIS_PER_DAY = 24 * 60 * 60 * 1000L;
		
		LocalDateTime.now();
		Date prr = java.sql.Date.valueOf(LocalDate.now());
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(prr);
		calendar2.set(Calendar.HOUR_OF_DAY, LocalDateTime.now().getHour());
		calendar2.set(Calendar.MINUTE, LocalDateTime.now().getMinute());
		calendar2.set(Calendar.SECOND, 0);
		calendar2.set(Calendar.MILLISECOND, 0);
		Date date2 = calendar2.getTime();
					
		if(ex.getStatus().equals("ACTIVE")) {
			if(Math.abs(date2.getTime() - date1.getTime()) >= MILLIS_PER_DAY) {
				System.out.println("Examination can be cancelled!");
				ex.setStatus("FREE");
				ex.setPatient(null);
				Patient p = patientRepository.findOneById(patientId);
				p.getExaminations().remove(ex);
				Dermatologist d = dermatologistRepository.findOneById(ex.getDermatologist().getId());
				List<Examination> dermatologistExaminations = d.getExaminations();
				examinationRepository.save(ex);
				for(Examination examination : dermatologistExaminations)
					if(examination.getId() == ex.getId()) {
						examination.setStatus("FREE");
						examination.setPatient(null);
						examinationRepository.save(ex);
					}									
			}else {
				System.out.println("Examination cannot be cancelled!");
				return null;
			}	
		}
		return ex;
	}
	
	public List<Examination> getPatientActiveExaminations(Long patientId){
		Patient p = patientRepository.findOneById(patientId);
		List<Examination> activeExaminations = new ArrayList<Examination>();
		
		for(Examination ex : p.getExaminations()) 
			if(ex.getStatus().equals("ACTIVE"))
				activeExaminations.add(ex);
		
		return activeExaminations;
	}
	
	public Examination updateExaminationPrice(Long id, double price) {
		Examination ex = examinationRepository.findOneById(id);
		ex.setPrice(price);
		examinationRepository.save(ex);
		return ex;
	}
}
