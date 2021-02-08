package com.example.pharmacySystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.example.pharmacySystem.auth.RestAuthenticationEntryPoint;
import com.example.pharmacySystem.auth.TokenAuthenticationFilter;
import com.example.pharmacySystem.security.TokenUtils;
import com.example.pharmacySystem.service.LoginService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(loginService).passwordEncoder(passwordEncoder());
	}
	
	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	
	@Autowired
	TokenUtils tokenUtils;
	
	@Autowired
	private LoginService loginService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
		.authorizeRequests().requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
		.antMatchers("/register").permitAll().antMatchers("/login").permitAll()
		.antMatchers("/getUser").permitAll().antMatchers("/h2-console/**").permitAll()
		
		//.antMatchers("/cancelMedicationReservation/*").permitAll()
		.antMatchers("/createCounseling").permitAll()
		.antMatchers("/getPatientActiveCounselings/*").permitAll()
		.antMatchers("/getPharmacistFromCounseling/*").permitAll()
		.antMatchers("/cancelCounseling/*").permitAll()
		.antMatchers("/getPharmacistCounselings/*").permitAll()
		.antMatchers("/getPatientSubscriptions/*").permitAll()
		.antMatchers("/subscribeToPharmacy/*/*").permitAll()
		.antMatchers("/unsubscribeFromPharmacy/*/*").permitAll()
		.antMatchers("/getPharmacyPromotions/*").permitAll()
		.antMatchers("/createPromotion").permitAll()
		
		.antMatchers("/", "/*.html", "/favicon.ico","/*.js", "/*.css").permitAll()
		.anyRequest().authenticated().and()
		.cors().and()
		.addFilterBefore(new TokenAuthenticationFilter(tokenUtils, loginService),
				BasicAuthenticationFilter.class);

		http.csrf().disable();
		http.headers().frameOptions().disable();
	}
	
	@Override
	public void configure(WebSecurity web) {
	
		web.ignoring().antMatchers(HttpMethod.POST, "/sendAccountConfirmation", "/examinationScheduled/*", "/changePassword", "/medicationReservationMade/*",
									"/counselingScheduled/*");
		
		web.ignoring().antMatchers(HttpMethod.GET, "/activateAccount/*", "/getAllPharmacies", "getPharmacy/*", "/getPharmacyMedications/*",
									"/getAllPharmacists", "/getPharmacyPharmacists/*", "/getUserByEmail/*", "/getAllUsers", "/getAllSystemAdmins",
									"/getSystemAdminFromUserId/*", "/getPatientByUserId/*", "/getPatientById/*", "/getAllPatients", "/getPharmacy/*",
									"/getAdminsPharmacy/*", "/getDermatologistPharmacies/*", "/getAllPharmacyAdmins", "/getPharmacyAdmin/*", "/getAllDermatologists",
									"/getAllMedicationTypes", "/getPharmacistPharmacy/*", "/getPharmacyByExaminationId/*", "/getAllMedications",
									"/getMedicationsNotInPharmacy/*", "/getMedicationById/*", "/findMedicationsByName/*", "/getPharmacyDermatologists/*",
									"/getDermatologistsNotInPHarmacy/*", "/getPatientAllergicMedications/*", "/getAllDermatologistWorkingHours",
									"/getOneDermatologistActiveWorkingHours/*", "/getPharmacyAdminFromUserId/*", "/getAllPharmacyExaminations/*",
									"/getAllMedicationsInPharmacies", "/getAllExaminations", "/findAllMedsByPharmacyId/*", "/findOneByPharmacyIdAndMedicationId/*/*",
									"/getAllDermatologistExaminations/*", "/getDermatologistPharmacyExaminations/*/*", "/getDermatologistPharmacyExaminationsByStatus/*/*/*",
									"/getByStatusAndDermatologistId/*/*", "/getExaminationsByPharmacyIdAndStatus/*/*", "/getDermatologistByExaminationId/*",
									"/getPatientActiveExaminations/*", "/getExaminationById/*", "/getMedicationInPharmacyFromReservation/*", "/getMedicationFromReservation/*",
									"/getMedInPharmacyById/*", "/findPatientActiveMedicationReservations/*/*", "/getPatientMedicationReservations/*", "/getPharmaciesWithAvailablePharmacists/*/*",
									"/getAvailablePharmacists/*/*/*");
	}
}
