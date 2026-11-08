package com.college.hospitalmanagement.dto;
import java.time.LocalDate;
public record NurseResponseDTO(Long id, String fullName, String gender, LocalDate dateOfBirth, String mobile, String email, String qualification, String department, Integer yearsOfExperience, String shift, LocalDate joiningDate, String address, String status, String loginEmail) { }
