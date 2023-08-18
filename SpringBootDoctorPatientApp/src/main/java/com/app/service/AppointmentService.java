package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.model.Appointment;
import com.app.repository.AppointmentRepository;

@Service
@Transactional
public class AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;
	
	public void insertAppointment(Appointment appointment) {
		appointmentRepository.save(appointment);
	}

	public List<Appointment> getAppointmentsByDoctorId(long doctorId) {
		List<Appointment> appointments = appointmentRepository.findByDoctorId(doctorId);
		
		return appointments;
	}
	
	public List<Appointment> getAppointmentsByPatientId(long patientId) {
		List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);
		
		return appointments;
	}
	
	public void acceptAppointent(Long a_id) {
		appointmentRepository.acceptAppointment(a_id);
	}
	
	public void declineAppointent(Long a_id) {
		appointmentRepository.declineAppointment(a_id);
	}
}
