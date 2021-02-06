package com.example.pharmacySystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.pharmacySystem.model.Examination;
import com.example.pharmacySystem.model.Patient;
import com.example.pharmacySystem.model.User;
import com.example.pharmacySystem.repository.ExaminationRepository;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private ExaminationRepository examinationRepository;
	
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
}
