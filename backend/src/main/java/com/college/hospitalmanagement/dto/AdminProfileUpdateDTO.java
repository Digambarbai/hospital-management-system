package com.college.hospitalmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdminProfileUpdateDTO(
        @NotBlank(message = "Employee ID is required")
        @Size(max = 50, message = "Employee ID must be 50 characters or fewer") String empId,
        @NotBlank(message = "Name is required") String name,
        @NotBlank(message = "Email is required") @Email(message = "Enter a valid email address") String email
) { }
