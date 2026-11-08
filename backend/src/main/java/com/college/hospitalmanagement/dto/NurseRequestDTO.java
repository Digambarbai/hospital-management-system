package com.college.hospitalmanagement.dto;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
public record NurseRequestDTO(
 @NotBlank String fullName, @NotBlank String gender, @Past LocalDate dateOfBirth,
 @NotBlank @Pattern(regexp="^[+0-9 ()-]{7,30}$") String mobile, @Email String email,
 @NotBlank String qualification, @NotBlank String department, @NotNull @Min(0) Integer yearsOfExperience,
 @NotBlank String shift, @NotNull LocalDate joiningDate, @NotBlank @Size(max=500) String address,
 @NotBlank String status, @NotBlank @Email String loginEmail,
 @Pattern(regexp="^$|.{6,}", message="Password must be at least 6 characters") String password, String confirmPassword) { }
