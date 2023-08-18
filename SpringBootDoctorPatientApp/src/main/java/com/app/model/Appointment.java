package com.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="appointment")
public class Appointment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long a_id;
	@Column(nullable=false)
	private String reason;
	@Column(nullable=false)
	private String date;
	@Column(nullable=false)
	private String time;
	@Column(nullable=false)
	private String status;
	
	@ManyToOne
	@JoinColumn(name="d_id_FK")
	private Doctor map_doctor;
	
	@ManyToOne
	@JoinColumn(name="p_id_FK")
	private Patient map_patient;

	
	public Long getA_id() {
		return a_id;
	}

	public void setA_id(Long a_id) {
		this.a_id = a_id;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Doctor getMap_doctor() {
		return map_doctor;
	}

	public void setMap_doctor(Doctor map_doctor) {
		this.map_doctor = map_doctor;
	}

	public Patient getMap_patient() {
		return map_patient;
	}

	public void setMap_patient(Patient map_patient) {
		this.map_patient = map_patient;
	}
	
	
	

}
