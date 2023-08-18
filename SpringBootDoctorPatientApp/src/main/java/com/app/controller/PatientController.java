package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.exception.PatientNotFoundException;
import com.app.model.Appointment;
import com.app.model.Patient;
import com.app.service.AppointmentService;
import com.app.service.PatientService;

@Controller
@RequestMapping("patient/")
public class PatientController {
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private AppointmentService appointmentService;

	@GetMapping("patientLogin")
	public String patientLogin() {

		return "login";
	}
	
	@GetMapping("patientForm")
	public String patientForm(Patient patient, Model model) {
		model.addAttribute("patient", patient);
		model.addAttribute("pageTitle", "Patient Registration Form");
		return "patient_form";
	}
	
	@PostMapping("addPatient")
	public String addPatient(Patient patient,RedirectAttributes redirectAttributes) {
		patientService.insertPatient(patient);
		redirectAttributes.addFlashAttribute("message", "Your have been registered successfully!" );
		return "redirect:/patient/patientForm?success";
	}
	
	@GetMapping("updateProfile")
	public String updateProfile(@AuthenticationPrincipal UserDetails loggedInPatient, Model model,RedirectAttributes redirectAttributes) {
		try {
			String email = loggedInPatient.getUsername();
			Patient patient = patientService.findPatientByEmail(email);
			model.addAttribute("patient", patient);
			model.addAttribute("pageTitle","Updating Patient "+ patient.getFirst_name()+ ", "+patient.getLast_name());
			return "patient_update_form";
		}catch(PatientNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/";
	}
	
	@PostMapping("addUpdatedPatient")
	public String addUpdatedPatient(Patient patient,RedirectAttributes redirectAttributes) {
		patientService.insertPatient(patient);
		redirectAttributes.addFlashAttribute("message", "Your information uptaded successfully!" );
		return "redirect:/patient/updateProfile?success";
	}
	
	@GetMapping("viewPatientProfile")
	public String viewPatientProfile(@AuthenticationPrincipal UserDetails loggedInPatient,Model model) {
		String email = loggedInPatient.getUsername();
		Patient patient = patientService.findPatientByEmail(email);
		model.addAttribute("patient",patient);
		model.addAttribute("pageTitle", "My Profile");
		return "view_patient_profile";
	}
	
	
	@GetMapping("viewUpcomingAppointment")
	public String viewDoctorAppointment(@AuthenticationPrincipal UserDetails loggedInPatient,Model model) {
		String email = loggedInPatient.getUsername();
		Patient patient = patientService.findPatientByEmail(email);
		List<Appointment> appointments = appointmentService.getAppointmentsByPatientId(patient.getP_id());

		model.addAttribute("appointmentList",appointments);
		model.addAttribute("pageTitle", "Doctor appointment list");
		return "view_doctor_appointment_list";
	}
}
