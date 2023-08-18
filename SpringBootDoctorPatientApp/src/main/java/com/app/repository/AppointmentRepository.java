package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.app.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{
    @Query("SELECT a FROM Appointment a WHERE a.map_doctor.id = :doctorId")
    List<Appointment> findByDoctorId(long doctorId);
    
    @Query("SELECT a FROM Appointment a WHERE a.map_patient.id = :patientId")
    List<Appointment> findByPatientId(long patientId);
    
    @Modifying
    @Query("update Appointment a set a.status='Accepted!' where a.a_id= :a_id")
    public void acceptAppointment(Long a_id);
    
    @Modifying
    @Query("update Appointment a set a.status='Declined!' where a.a_id= :a_id")
    public void declineAppointment(Long a_id);

}
