package com.example.pharmacySystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.pharmacySystem.model.User;
import com.example.pharmacySystem.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/getUserByEmail/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email){
	
		User myUser = userService.findOneByEmail(email);
		return new ResponseEntity<User>(myUser, HttpStatus.OK);
	}
}
