package com.example.pharmacySystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
		.antMatchers("/getUserRole").permitAll().antMatchers("/sendAccountConfirmation").permitAll()
		.antMatchers("/activateAccount/*").permitAll()
		.antMatchers("/getAllPharmacies").permitAll()
		.antMatchers("/getPharmacy/*").permitAll()
		.antMatchers("/getPharmacyMedications/*").permitAll()
		.antMatchers("/changePassword").permitAll()
		.antMatchers("/updateSystemAdmin").permitAll()
		.antMatchers("/getAllSystemAdmins").permitAll()
		.antMatchers("/getAllPatients").permitAll()
		.antMatchers("/getAllUsers").permitAll()
		.antMatchers("/getAllPharmacyAdmins").permitAll()
		.antMatchers("/addPharmacyAdmin").permitAll()
		.antMatchers("/addSystemAdmin").permitAll()
		.antMatchers("/getPharmacyAdmin/*").permitAll()
		.antMatchers("/addPharmacy").permitAll()
		.antMatchers("/getAllDermatologists").permitAll()
		.antMatchers("/addDermatologist").permitAll()
		.antMatchers("/addSupplier").permitAll()
		.antMatchers("/getAllMedicationTypes").permitAll()
		.antMatchers("/addMedication").permitAll()
		.antMatchers("/getAllMedications").permitAll()
		.antMatchers("/addPatientAllergicMedication/*").permitAll()
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
	
	}
}
