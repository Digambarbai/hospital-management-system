package com.college.hospitalmanagement.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DoctorPasswordResetDTO(
        @NotBlank(message = "New password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        String newPassword,
        @NotBlank(message = "Confirm password is required")
        String confirmPassword
) {
    @AssertTrue(message = "Password and confirm password must match")
    public boolean passwordsMatch() {
        return newPassword != null && newPassword.equals(confirmPassword);
    }
}
