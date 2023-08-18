package com.app.security;

import com.app.model.Patient;
import com.app.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.app.model.Doctor;
import com.app.repository.DoctorRepository;

public class DoctorDetailsService implements UserDetailsService {
	
	@Autowired
	private DoctorRepository doctorRepository;
	@Autowired
	private PatientRepository patientRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Doctor doctor = doctorRepository.getDoctorByEmail(email);
		if(doctor != null) {
			return new DoctorDetails(doctor);
		}
		Patient patient = patientRepository.getPatientByEmail(email);
		if (patient != null){
			return new PatientDetails(patient);
		}
		throw new UsernameNotFoundException("Could not find any user with email: "+ email);
	}

}
