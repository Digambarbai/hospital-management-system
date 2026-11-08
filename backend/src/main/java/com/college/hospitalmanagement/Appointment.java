package com.college.hospitalmanagement;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, unique = true, length = 20) private String appointmentCode;
    @ManyToOne(optional = false, fetch = FetchType.LAZY) @JoinColumn(name = "patient_id", nullable = false) private Patient patient;
    @ManyToOne(optional = false, fetch = FetchType.LAZY) @JoinColumn(name = "doctor_id", nullable = false) private Doctor doctor;
    @Column(nullable = false, length = 100) private String department;
    @Column(nullable = false) private LocalDate appointmentDate;
    @Column(nullable = false) private LocalTime appointmentTime;
    @Column(nullable = false, length = 30) private String appointmentType;
    @Column(nullable = false, length = 500) private String reason;
    @Column(nullable = false, length = 20) private String status;
    @Column(length = 1000) private String notes;
    public Long getId(){return id;} public String getAppointmentCode(){return appointmentCode;} public Patient getPatient(){return patient;} public Doctor getDoctor(){return doctor;} public String getDepartment(){return department;} public LocalDate getAppointmentDate(){return appointmentDate;} public LocalTime getAppointmentTime(){return appointmentTime;} public String getAppointmentType(){return appointmentType;} public String getReason(){return reason;} public String getStatus(){return status;} public String getNotes(){return notes;}
    public void setAppointmentCode(String v){appointmentCode=v;} public void setPatient(Patient v){patient=v;} public void setDoctor(Doctor v){doctor=v;} public void setDepartment(String v){department=v;} public void setAppointmentDate(LocalDate v){appointmentDate=v;} public void setAppointmentTime(LocalTime v){appointmentTime=v;} public void setAppointmentType(String v){appointmentType=v;} public void setReason(String v){reason=v;} public void setStatus(String v){status=v;} public void setNotes(String v){notes=v;}
}
