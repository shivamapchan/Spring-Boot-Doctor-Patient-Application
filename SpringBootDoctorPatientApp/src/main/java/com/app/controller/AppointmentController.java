package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.exception.DoctorNotFoundException;
import com.app.model.Appointment;
import com.app.model.Doctor;
import com.app.model.Patient;
import com.app.service.AppointmentService;
import com.app.service.DoctorService;
import com.app.service.PatientService;

@Controller
@RequestMapping("appointment/")
public class AppointmentController {
	
	@Autowired
	private DoctorService doctorService;
	
	@Autowired 
	private PatientService patientService;
	
	@Autowired
	private AppointmentService appointmentService;
		
	@GetMapping("appointmentForm/{doc_id}")
	public String appointmentForm(@PathVariable("doc_id") Long doc_id, Appointment appointment,
			@AuthenticationPrincipal UserDetails loggedInPatient,Model model, RedirectAttributes redirectAttributes) {
		try {
			// patient info
			String email = loggedInPatient.getUsername();
			Patient patient = patientService.findPatientByEmail(email);
			model.addAttribute("patient",patient);
			
			// doctor info
			Doctor doctor = doctorService.getById(doc_id);
			model.addAttribute("doctor", doctor);
			model.addAttribute("appointment",appointment);
			model.addAttribute("message","Applying appointment to "+ doctor.toString()+" ("+doctor.getSpecialty()+")");
			return "appointment_form";
		} catch (DoctorNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());

		}
		return "redirect:doctor/view_doctor";
	}
	
	@PostMapping("addAppointment")
	public String addAppointment(@ModelAttribute("appointment") Appointment appointment, RedirectAttributes redirectAttributes) {
		appointmentService.insertAppointment(appointment);
		redirectAttributes.addFlashAttribute("message", "You have successfully schedule a appointment");
		return "redirect:/patient/viewUpcomingAppointment";
	}
	
	@GetMapping("acceptAppointment/{a_id}")
	public String acceptAppointment(@PathVariable Long a_id) {
		appointmentService.acceptAppointent(a_id);
		return "redirect:/doctor/upcomingAppointment";
	}
	
	@GetMapping("declineAppointment/{a_id}")
	public String declineAppointment(@PathVariable Long a_id) {
		appointmentService.declineAppointent(a_id);
		return "redirect:/doctor/upcomingAppointment";
	}

}
