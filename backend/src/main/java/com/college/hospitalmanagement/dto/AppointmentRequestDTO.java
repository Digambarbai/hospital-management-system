package com.college.hospitalmanagement.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentRequestDTO(
        @NotNull Long patientId,
        @NotNull Long doctorId,
        @NotBlank @Size(max = 100) String department,
        @NotNull @FutureOrPresent LocalDate appointmentDate,
        @NotNull LocalTime appointmentTime,
        @NotBlank @Pattern(regexp = "Consultation|Follow-up|Emergency") String appointmentType,
        @NotBlank @Size(max = 500) String reason,
        @NotBlank @Pattern(regexp = "Scheduled|Confirmed|Completed|Cancelled") String status,
        @Size(max = 1000) String notes) { }
