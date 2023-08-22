package com.app.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.app.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.exception.DoctorNotFoundException;
import com.app.model.Doctor;
import com.app.model.Patient;
import com.app.repository.DoctorRepository;

@Service
public class DoctorService {
	
	@Autowired
	private DoctorRepository doctorRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Doctor insertDoctor(Doctor doctor) {
		
		boolean isUpdatingDoctor = (doctor.getD_id() != null);
		if(isUpdatingDoctor) {
			Doctor existingDoctor = doctorRepository.findById(doctor.getD_id()).get();
			if(doctor.getPassword().isEmpty()) {
				doctor.setPassword(existingDoctor.getPassword());
			} else {
				encodePassword(doctor);
				doctor.setRole(UserRole.DOCTOR);
			}
		}else {
			encodePassword(doctor);
			doctor.setRole(UserRole.DOCTOR);
		}
		
		return doctorRepository.save(doctor);
	}
	
	private void encodePassword(Doctor doctor) {
		String encodedPassword = passwordEncoder.encode(doctor.getPassword());
		doctor.setPassword(encodedPassword);
	}
	
	public List<Doctor> findAllDoctor(String keyword){
		if(keyword != null) {
			List<Doctor> docotrList = doctorRepository.getFilterDoctor(keyword);
			return docotrList;
		}
		List<Doctor> doctorList = doctorRepository.findAll();
		return doctorList;
	}
	
	public Doctor getById(Long id)throws DoctorNotFoundException {	
		try {
			return doctorRepository.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new DoctorNotFoundException("Could not find any Doctor with ID " + id);
		}	
		
	}
	
	public Doctor findDoctorByEmail(String email) {
		return doctorRepository.getDoctorByEmail(email);
	}
	
	public Doctor findAppointmentInfo(String email) {
		return doctorRepository.getAppointmentInfo(email);
	}
	
	public boolean isEmailUnique(String email) {
		Doctor doctorByEmail = doctorRepository.getDoctorByEmail(email);
		return doctorByEmail == null;
		
	}

}
