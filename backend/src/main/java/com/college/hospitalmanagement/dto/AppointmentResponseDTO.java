package com.college.hospitalmanagement.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentResponseDTO(Long id, String appointmentCode, Long patientId, String patientName,
        Long doctorId, String doctorName, String department, LocalDate appointmentDate, LocalTime appointmentTime,
        String appointmentType, String reason, String status, String notes) { }
