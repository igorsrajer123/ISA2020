package com.example.pharmacySystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.pharmacySystem.model.Patient;
import com.example.pharmacySystem.service.EmailService;
import com.example.pharmacySystem.service.PatientService;

@RestController
public class EmailController {

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PatientService patientService;
	
	@PostMapping(value = "/sendAccountConfirmation", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> sendAccountConfirmationEmail(@RequestBody String[] data){
		String email = data[0];
		String text = data[1];
		
		try {
			emailService.sendConfirmationEmail(email, text);
		}catch (MailException e) {
			e.printStackTrace();
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<String>("Email sent", HttpStatus.OK);
	}
	
	@GetMapping(value = "/activateAccount/{patientId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> activateAccount(@PathVariable String patientId){
		long id = Integer.parseInt(patientId);
		Patient myPatient = patientService.findOneByUserId(id);
		myPatient.getUser().setActivated(true);
		myPatient.setProcessed(true);
		myPatient = patientService.save(myPatient);
		return new ResponseEntity<String>("Account successfully activated!", HttpStatus.OK);
	}
	
	@PostMapping(value = "/examinationScheduled/{examinationId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> examinationScheduledNotification(@PathVariable("examinationId") Long id){
		emailService.examinationScheduledNotification(id);
		return new ResponseEntity<String>("Email sent.", HttpStatus.OK);
	}
}
