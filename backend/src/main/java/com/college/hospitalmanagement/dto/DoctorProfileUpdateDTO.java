package com.college.hospitalmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DoctorProfileUpdateDTO(
        @NotBlank(message = "Full name is required") @Size(max = 150) String fullName,
        @NotBlank(message = "Mobile number is required") @Pattern(regexp = "^[0-9+() -]{7,30}$", message = "Enter a valid mobile number") String mobile,
        @Email(message = "Enter a valid email address") @Size(max = 150) String email,
        @NotBlank(message = "Qualification is required") @Size(max = 150) String qualification
) { }
