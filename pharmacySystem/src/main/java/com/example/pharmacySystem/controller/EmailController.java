package com.example.pharmacySystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
