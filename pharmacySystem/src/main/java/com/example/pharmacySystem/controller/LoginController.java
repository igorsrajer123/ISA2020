package com.example.pharmacySystem.controller;

import java.io.IOException;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.pharmacySystem.auth.JwtAuthenticationRequest;
import com.example.pharmacySystem.model.Patient;
import com.example.pharmacySystem.model.User;
import com.example.pharmacySystem.model.UserTokenState;
import com.example.pharmacySystem.security.TokenUtils;
import com.example.pharmacySystem.service.LoginService;
import com.example.pharmacySystem.service.UserService;

@RestController
public class LoginController {

	@Autowired 
	private UserService userService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Patient> register(@RequestBody Patient patient) throws Exception {
		Patient myPatient = loginService.register(patient);
		
		if(myPatient == null) return new ResponseEntity<Patient>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<Patient>(myPatient, HttpStatus.OK);
	}
	
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserTokenState> login(@RequestBody JwtAuthenticationRequest request, HttpServletResponse response) throws AuthenticationException, IOException{
		User myUser = new User(request.getUsername(), request.getPassword());
		myUser = loginService.login(myUser);
		
		if(myUser == null) return new ResponseEntity<UserTokenState>(HttpStatus.NOT_FOUND);
		
		final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		User user = (User) authentication.getPrincipal();
		String token = tokenUtils.generateToken(user.getUsername());
		int expires = tokenUtils.getExpiredIn();
		
		return ResponseEntity.ok(new UserTokenState(token, expires));
	}
	
	@GetMapping(value = "/getUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUser(HttpServletRequest request){
		String myToken = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(myToken);
		User myUser = userService.findOneByEmail(email);
		
		if(myUser == null) return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<User>(myUser, HttpStatus.OK);
	}
	
	@PostMapping(value = "/changePassword", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> changePass(@RequestBody String[] data, HttpServletRequest request){
		String myToken = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(myToken);
		User myUser = userService.findOneByEmail(email);
		
		if(myUser == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		myUser.setPassword(passwordEncoder.encode(data[0]));
		myUser.setFirstLogin(false);
		myUser = userService.save(myUser);
		
		return new ResponseEntity<User>(myUser, HttpStatus.OK);
	}
}
