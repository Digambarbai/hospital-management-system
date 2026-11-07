package com.college.hospitalmanagement.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DoctorResponseDTO(
    Long id, String fullName, String gender, LocalDate dateOfBirth, String mobile,
    String email, String qualification, String specialization, String department,
    Integer yearsOfExperience, BigDecimal consultationFee, String status, String loginEmail
) { }
