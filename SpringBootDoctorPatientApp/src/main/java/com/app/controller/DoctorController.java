package com.app.controller;

import java.io.IOException;
import java.util.List;

import com.app.exception.DoctorNotFoundException;
import com.app.model.Appointment;
import com.app.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.model.Doctor;
import com.app.service.DoctorService;
import com.app.utility.FileUploadUtility;

@Controller
@RequestMapping("doctor/")
public class DoctorController {
	
	@Autowired
	private DoctorService doctorService;

	@Autowired
	private AppointmentService appointmentService;

	@GetMapping("doctorLogin")
	public String doctorLogin() {

		return "login_doctor";
	}
	
	@GetMapping("doctorForm")
	public String addDoctorForm(Doctor doctor, Model model) {
		model.addAttribute("doctor", doctor);
		model.addAttribute("pageTitle", "Doctor Registration Form");
		return "doctor_form";
	}
	
	@PostMapping("addDoctor")
	public String addDoctor(@ModelAttribute("doctor") Doctor doctor, RedirectAttributes redirectAttributes, 
			@RequestParam("image") MultipartFile multipartFile) throws IOException {
		if(!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			doctor.setPhoto(fileName);
			Doctor savedDoctor = doctorService.insertDoctor(doctor);
			String uploadDir = "doctor-photos/" + savedDoctor.getD_id();
			FileUploadUtility.cleanDir(uploadDir);
			FileUploadUtility.saveFile(uploadDir,fileName,multipartFile);
		}else {
			if(doctor.getPhoto().isEmpty()) doctor.setPhoto(null);
			doctorService.insertDoctor(doctor);
			//redirectAttributes.addFlashAttribute("message", "Your have successfully registered in our app!" );
			
		}
	
		return "redirect:/doctor/doctorForm?success";
	}
	
	@GetMapping("updateProfile")
	public String updateProfile(@AuthenticationPrincipal UserDetails loggedInDoctor, Model model,RedirectAttributes redirectAttributes) {
		try {
			String email = loggedInDoctor.getUsername();
			Doctor doctor = doctorService.findDoctorByEmail(email);
			model.addAttribute("doctor", doctor);
			model.addAttribute("pageTitle","Updating doctor "+ doctor.getFirst_name()+ ", "+doctor.getLast_name());
			return "doctor_update_form";
		}catch(DoctorNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/";
	}
	
	@PostMapping("addUpdatedDoctor")
	public String addUpdatedDoctor(Doctor doctor,RedirectAttributes redirectAttributes,
			@RequestParam("image") MultipartFile multipartFile)throws IOException {
		if(!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			doctor.setPhoto(fileName);
			Doctor savedDoctor = doctorService.insertDoctor(doctor);
			String uploadDir = "doctor-photos/" + savedDoctor.getD_id();
			FileUploadUtility.cleanDir(uploadDir);
			FileUploadUtility.saveFile(uploadDir,fileName,multipartFile);
		}else {
			if(doctor.getPhoto().isEmpty()) doctor.setPhoto(null);
			doctorService.insertDoctor(doctor);
			//redirectAttributes.addFlashAttribute("message", "Your information uptaded successfully!" );			
		}
		return "redirect:/doctor/updateProfile?success";
	}

	
	@GetMapping("view_doctor")
	public String viewAllDoctor(@ModelAttribute("doctor")Doctor doctor, Model model,@Param("keyword") String keyword) {
		List<Doctor> doctorList = doctorService.findAllDoctor(keyword);
		model.addAttribute("doctorList", doctorList);
		model.addAttribute("pageTitle", "List of Doctor");
		return "view_doctor";
	}
	
	@GetMapping("viewDoctorProfile")
	public String viewDoctorProfile(@AuthenticationPrincipal UserDetails loggedInDoctor,Model model) {
		String email = loggedInDoctor.getUsername();
		Doctor doctor = doctorService.findDoctorByEmail(email);
		model.addAttribute("doctorObject",doctor);
		model.addAttribute("pageTitle", "My Profile");
		return "view_doctor_profile";
	}
	
	@GetMapping("upcomingAppointment")
	public String viewPatientAppointment(@AuthenticationPrincipal UserDetails loggedInDoctor,Model model) {
		String email = loggedInDoctor.getUsername();
		Doctor doctor = doctorService.findDoctorByEmail(email);
		List<Appointment> appointments = appointmentService.getAppointmentsByDoctorId(doctor.getD_id());

		model.addAttribute("doctorObject",doctor);
		model.addAttribute("appointmentlist",appointments);
		model.addAttribute("pageTitle", "Patient appointment list");
		return "view_patient_appointment_list";
	}
	
}
