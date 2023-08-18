package com.app.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;


@Entity
@Table(name="doctor")
public class Doctor {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long d_id;
	@Column(name="f_name", length = 25,  nullable=false  )
	private String first_name;
	@Column(name="l_name", length = 25,  nullable=false  )
	private String last_name;
	@Column(length = 128, nullable = false, unique = true)
	private String email;
	@Column(length = 64, nullable = false)
	private String password;
	@Column(length = 64)
	private String photo;
	@Column(length = 64, nullable=false)
	private String specialty;
	@Column(name="street", length = 200)
	private String street_address;
	@Column(length = 20, nullable=false)
	private String city;
	@Column(length = 20, nullable=false)
	private String state;
	@Column(nullable=false)
	private Long phone;
	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	private UserRole role = UserRole.DOCTOR;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="map_doctor",cascade=CascadeType.ALL)
	private Set<Appointment> appointment = new HashSet<Appointment>();

	public Long getD_id() {
		return d_id;
	}

	public void setD_id(Long d_id) {
		this.d_id = d_id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getStreet_address() {
		return street_address;
	}

	public void setStreet_address(String street_address) {
		this.street_address = street_address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public Set<Appointment> getAppointment() {
		return appointment;
	}

	public void setAppointment(Set<Appointment> appointment) {
		this.appointment = appointment;
	}

	@Override
	public String toString() {
		return "Dr. " + first_name + ", " + last_name;
	}
	
	
	
	
}
