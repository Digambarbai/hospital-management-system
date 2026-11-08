package com.college.hospitalmanagement.dto;
import java.time.LocalDate;
public record PatientResponseDTO(Long id,String patientCode,String fullName,String gender,LocalDate dateOfBirth,String mobile,String email,String bloodGroup,String address,String emergencyContact,LocalDate registrationDate,String status){}
