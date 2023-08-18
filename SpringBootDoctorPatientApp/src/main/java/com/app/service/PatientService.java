package com.app.service;

import com.app.model.UserRole;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.exception.PatientNotFoundException;
import com.app.model.Patient;
import com.app.repository.PatientRepository;

@Service
public class PatientService {
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void insertPatient(Patient patient) {
		boolean isUpdatingPatient = (patient.getP_id() != null);
		if(isUpdatingPatient) {
			Patient exisringPatient = patientRepository.findById(patient.getP_id()).get();
			if(patient.getPassword().isEmpty()) {
				patient.setPassword(exisringPatient.getPassword());
			} else {
				encodePassword(patient);
				patient.setRole(UserRole.PATIENT);
			}
		}else {
			encodePassword(patient);
			patient.setRole(UserRole.PATIENT);
		}
		
		patientRepository.save(patient);
	}
	
	private void encodePassword(Patient patient) {
		String encodedPassword = passwordEncoder.encode(patient.getPassword());
		patient.setPassword(encodedPassword);
	}
	
	public Patient findPatientByEmail(String email) {
		return patientRepository.getPatientByEmail(email);
	}
	
	public Patient getById(Long id) throws PatientNotFoundException {
		try {
			return patientRepository.findById(id).get();
		}catch(NoSuchElementException ex){
			throw new PatientNotFoundException("Could not find patient with ID: "+id);
		}
	}
	
	public boolean isEmailUnique(String email) {
		Patient patientByEmail = patientRepository.getPatientByEmail(email);
		
		return patientByEmail == null;
		
	}

}
