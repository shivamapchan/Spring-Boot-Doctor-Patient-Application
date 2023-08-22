package com.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

	@Bean
	UserDetailsService doctorDetailService() {
		return new DoctorDetailsService();
	}


	// Encode user password
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests()
				.antMatchers("/patient/check_email/**","/doctor/check_email/**").permitAll()
				.antMatchers("/patient/patientForm", "/patient/addPatient").permitAll()
				.antMatchers("/doctor/doctorForm", "/doctor/addDoctor","/images/doctorMedium.png/**").permitAll()
				.antMatchers("/patient/patientLogin").permitAll()
				.antMatchers("/doctor/doctorLogin").permitAll()
				.anyRequest().authenticated()
				.and().formLogin(login -> login
						.loginPage("/login")
						.usernameParameter("email")
						.loginProcessingUrl("/login")
						.permitAll())
				.logout(logout -> logout
						.logoutSuccessUrl("/login?logout")
						.permitAll());
		return http.build();
	}

	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**",
        		"/fontawesome/**", "/webfonts/**", "/style.css");
    }

}

