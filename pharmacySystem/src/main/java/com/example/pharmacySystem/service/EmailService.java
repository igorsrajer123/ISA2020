package com.example.pharmacySystem.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.pharmacySystem.model.Counseling;
import com.example.pharmacySystem.model.Examination;
import com.example.pharmacySystem.model.MedicationReservation;
import com.example.pharmacySystem.model.Offer;
import com.example.pharmacySystem.model.Patient;
import com.example.pharmacySystem.model.Pharmacy;
import com.example.pharmacySystem.model.Promotion;
import com.example.pharmacySystem.model.User;
import com.example.pharmacySystem.repository.CounselingRepository;
import com.example.pharmacySystem.repository.ExaminationRepository;
import com.example.pharmacySystem.repository.MedicationReservationRepository;
import com.example.pharmacySystem.repository.OfferRepository;
import com.example.pharmacySystem.repository.PharmacyRepository;
import com.example.pharmacySystem.repository.PromotionRepository;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private ExaminationRepository examinationRepository;
	
	@Autowired
	private MedicationReservationRepository reservationRepository;
	
	@Autowired
	private CounselingRepository counselingRepository;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private PromotionRepository promotionRepository;
	
	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PatientService patientService;
	
	@Async
	public void sendConfirmationEmail(String email, String text) throws MailException, InterruptedException{
		System.out.println("Mail is sending...");
		User myUser = userService.findOneByEmail(email);
		Patient myPatient = patientService.findOneByUserId(myUser.getId());
		String path = "http://localhost:8080/activateAccount/" + myUser.getId();
		
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo("isapsw123@gmail.com");
		msg.setFrom(environment.getProperty("spring.mail.username"));
		msg.setSubject("Account Activation");
		
		if(text.equals("Approved"))
			msg.setText("Confirm you Pharmacy System account by clicking the link: " + path);
		else
			msg.setText("Your request for registration cannot be approved. Reason is: " + text);
		
		javaMailSender.send(msg);
		patientService.save(myPatient);
		System.out.println("Email sent!");
	}
	
	@Async
	public void examinationScheduledNotification(Long id) {
		Examination ex = examinationRepository.findOneById(id);
		
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo("isapsw123@gmail.com");
		msg.setFrom(environment.getProperty("spring.mail.username"));
		msg.setSubject("Examination Scheduled");
		msg.setText("Your examination has been scheduled for " + ex.getDate().toString() + " at " + ex.getTime());
		
		javaMailSender.send(msg);
		System.out.println("Email sent!");
	}
	
	@Async
	public void medicationReservedNotification(Long id) {
		MedicationReservation mr = reservationRepository.findOneById(id);
		Random rand = new Random();
		
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo("isapsw123@gmail.com");
		msg.setFrom(environment.getProperty("spring.mail.username"));
		msg.setSubject("Medication Reserved");
		msg.setText("You have reserved a medication and have to pick it up until " + mr.getPickUpDate() + 
					"\n\nReservation number: " + mr.getId() +"" + rand.nextInt(10000));
		
		javaMailSender.send(msg);
		System.out.println("Email sent!");
	}
	
	@Async
	public void counselingScheduledNotification(Long id) {
		Counseling c = counselingRepository.findOneById(id);
		
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo("isapsw123@gmail.com");
		msg.setFrom(environment.getProperty("spring.mail.username"));
		msg.setSubject("Counseling Scheduled");
		msg.setText("Your counseling with pharmacist has been scheduled for " + c.getDate() + " at " + c.getFrom());
		
		javaMailSender.send(msg);
		System.out.println("Email sent!");
	}
	
	@Async
	public void notifyAllSubscribers(Long promotionId) {
		Promotion promotion = promotionRepository.findOneById(promotionId);	
		Pharmacy pharmacy = pharmacyRepository.findOneById(promotion.getPharmacy().getId());
		List<Patient> allPharmacySubscribers = pharmacy.getSubscriptions();
		
		SimpleMailMessage msg = new SimpleMailMessage();
		
		for(Patient p : allPharmacySubscribers) {
			msg.setTo("isapasw123@gmail.com"); //p.getEmail()
			msg.setFrom(environment.getProperty("spring.mail.username"));
			msg.setSubject("New Promotion");
			msg.setText("Pharmacy " + pharmacy.getName() + "(" + pharmacy.getCity() + ")" + " you are subscribed to has a new discount:\n\n" +
						promotion.getText() + " until " + promotion.getUntilDate() + "!");
			
			javaMailSender.send(msg);
			System.out.println("Email sent to " + p.getUser().getEmail() + ".");
		}
	}
	
	@Async
	public void notifySupplier(Long offerId) {
		Offer offer = offerRepository.findOneById(offerId);
		
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo("isapsw123@gmail.com"); //suppliers mail here 
		msg.setFrom(environment.getProperty("spring.mail.username"));
		msg.setSubject("Offer Accepted");
		msg.setText("Your offer for pharmacy order has been accepted!\n\n Date of delivery is:" + offer.getDateOfDelivery());
		
		javaMailSender.send(msg);
		System.out.println("Email sent!");
	}
}
