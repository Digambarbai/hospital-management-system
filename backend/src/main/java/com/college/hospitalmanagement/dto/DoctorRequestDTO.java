package com.college.hospitalmanagement.dto;

import com.college.hospitalmanagement.validation.CreateValidation;
import com.college.hospitalmanagement.validation.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@PasswordMatches
public record DoctorRequestDTO(
    @NotBlank String fullName,
    @NotBlank String gender,
    @Past LocalDate dateOfBirth,
    @NotBlank @Pattern(regexp = "^[+0-9 ()-]{7,30}$") String mobile,
    @Email String email,
    @NotBlank String qualification,
    @NotBlank String specialization,
    @NotBlank String department,
    @NotNull @Min(0) Integer yearsOfExperience,
    @Min(0) BigDecimal consultationFee,
    @NotBlank String status,
    @NotBlank @Email String loginEmail,
    @NotBlank(groups = CreateValidation.class) @Size(min = 6) String password,
    @NotBlank(groups = CreateValidation.class) String confirmPassword
) { }
