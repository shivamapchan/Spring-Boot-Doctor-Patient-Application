package com.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.app.model.Patient;
import com.app.repository.PatientRepository;

public class PatientDetailsService implements UserDetailsService {
	
	@Autowired
	private PatientRepository patientRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Patient patient = patientRepository.getPatientByEmail(email);
		if(patient != null) {
			return new PatientDetails(patient);
		}
		throw new UsernameNotFoundException("Could not find any patient with email: "+ email);
	}

}
