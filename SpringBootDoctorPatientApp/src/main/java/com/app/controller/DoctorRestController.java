package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.service.DoctorService;

@RestController
public class DoctorRestController {
	
	@Autowired
	private DoctorService doctorService;
	
	@PostMapping("/doctor/check_email")
	public String checkDuplicateEmail(@Param("email") String email) {
		return doctorService.isEmailUnique(email)? "OK" : "Duplicate";
	}

}
