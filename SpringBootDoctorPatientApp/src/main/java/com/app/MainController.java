package com.app;

import com.app.security.DoctorDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping()
	public String homePage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if (principal instanceof DoctorDetails) {
			return "index_doctor";
		} else {
			return "index";
		}
	}

	@GetMapping("login")
	public String doctorPatientLogin() {
		return "login";
	}
}
