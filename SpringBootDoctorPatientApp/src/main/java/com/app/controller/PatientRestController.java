package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.service.PatientService;

@RestController
public class PatientRestController {
	
	@Autowired
	private PatientService patientService;

	@PostMapping("/patient/check_email")
	public String checkDuplicateEmail(@Param("email") String email) {
		return patientService.isEmailUnique(email)? "OK" : "Duplicate";
	}

}
