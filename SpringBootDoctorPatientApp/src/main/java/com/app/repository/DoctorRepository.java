package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.model.Doctor;

public interface DoctorRepository  extends JpaRepository<Doctor, Long>{
	
	// login & view profile
	@Query("SELECT d FROM Doctor d WHERE d.email= :email")
	public Doctor getDoctorByEmail(@Param("email") String email);
		
	@Query("select p.first_name,p.last_name, a.reason,a.date from Patient p Inner Join Appointment a on p.p_id = a.a_id "
			+ "inner join Doctor d where d.email=:email")
	public Doctor getAppointmentInfo(@Param("email") String email);

//	@Query("SELECT d FROM Doctor d WHERE d.sepcialty LIKE %?1% ")
//	public List<Doctor> getFilterDoctor(String keyword);
	
	@Query("SELECT d FROM Doctor d WHERE d.first_name LIKE %?1% OR d.last_name LIKE %?1% OR d.specialty LIKE %?1%")
	public List<Doctor> getFilterDoctor(String key);

}