package com.college.hospitalmanagement;

import com.college.hospitalmanagement.dto.*;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AppointmentService {
    private final AppointmentRepository appointments; private final PatientRepository patients; private final DoctorRepository doctors;
    public AppointmentService(AppointmentRepository appointments, PatientRepository patients, DoctorRepository doctors){this.appointments=appointments;this.patients=patients;this.doctors=doctors;}
    public List<AppointmentResponseDTO> list(SessionUser user){return ("DOCTOR".equals(user.role()) ? appointments.findByDoctorId(user.id()) : appointments.findAll()).stream().map(this::response).toList();}
    public AppointmentResponseDTO get(Long id, SessionUser user){Appointment a=find(id); ensureVisible(a,user); return response(a);}
    @Transactional public AppointmentResponseDTO create(AppointmentRequestDTO r){Appointment a=new Appointment(); apply(r,a); a.setAppointmentCode("TMP-"+UUID.randomUUID().toString().replace("-","").substring(0,16)); a=appointments.saveAndFlush(a); a.setAppointmentCode(String.format("AP-%06d",a.getId())); return response(appointments.save(a));}
    @Transactional public AppointmentResponseDTO update(Long id, AppointmentRequestDTO r){Appointment a=find(id); apply(r,a); return response(appointments.save(a));}
    @Transactional public void delete(Long id){appointments.delete(find(id));}
    private Appointment find(Long id){return appointments.findById(id).orElseThrow(()->new IllegalArgumentException("Appointment not found: "+id));}
    private void ensureVisible(Appointment a, SessionUser user){if("DOCTOR".equals(user.role()) && !a.getDoctor().getId().equals(user.id())) throw new org.springframework.security.access.AccessDeniedException("Access denied");}
    private void apply(AppointmentRequestDTO r, Appointment a){Patient patient=patients.findById(r.patientId()).orElseThrow(()->new IllegalArgumentException("Patient not found: "+r.patientId())); Doctor doctor=doctors.findById(r.doctorId()).orElseThrow(()->new IllegalArgumentException("Doctor not found: "+r.doctorId())); if(!"Active".equalsIgnoreCase(doctor.getStatus())) throw new IllegalArgumentException("Selected doctor is inactive"); if(!doctor.getDepartment().equalsIgnoreCase(r.department().trim())) throw new IllegalArgumentException("Selected doctor does not belong to the department"); boolean differentSlot=a.getId()==null || !a.getDoctor().getId().equals(doctor.getId()) || !a.getAppointmentDate().equals(r.appointmentDate()) || !a.getAppointmentTime().equals(r.appointmentTime()); if(!"Cancelled".equals(r.status()) && differentSlot && appointments.existsByDoctorIdAndAppointmentDateAndAppointmentTimeAndStatusNot(doctor.getId(),r.appointmentDate(),r.appointmentTime(),"Cancelled")) throw new IllegalArgumentException("Doctor already has an appointment at this date and time"); a.setPatient(patient);a.setDoctor(doctor);a.setDepartment(doctor.getDepartment());a.setAppointmentDate(r.appointmentDate());a.setAppointmentTime(r.appointmentTime());a.setAppointmentType(r.appointmentType());a.setReason(r.reason().trim());a.setStatus(r.status());a.setNotes(r.notes()==null?null:r.notes().trim());}
    private AppointmentResponseDTO response(Appointment a){return new AppointmentResponseDTO(a.getId(),a.getAppointmentCode(),a.getPatient().getId(),a.getPatient().getFullName(),a.getDoctor().getId(),a.getDoctor().getFullName(),a.getDepartment(),a.getAppointmentDate(),a.getAppointmentTime(),a.getAppointmentType(),a.getReason(),a.getStatus(),a.getNotes());}
}
