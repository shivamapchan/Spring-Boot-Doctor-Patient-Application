package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
	
	// login & view profile
	@Query("SELECT p FROM Patient p WHERE p.email= :email")
	public Patient getPatientByEmail(@Param("email") String email);

}
