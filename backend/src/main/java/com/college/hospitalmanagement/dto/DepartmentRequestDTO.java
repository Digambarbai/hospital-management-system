package com.college.hospitalmanagement.dto;

import jakarta.validation.constraints.*;
import java.util.List;

public record DepartmentRequestDTO(
        @NotBlank @Size(max = 150) String name,
        @NotBlank @Pattern(regexp = "[A-Za-z0-9_-]{2,30}") String code,
        @Size(max = 1000) String description,
        Long headDoctorId,
        @NotNull List<Long> doctorIds,
        @NotNull List<Long> nurseIds,
        @NotBlank @Pattern(regexp = "Active|Inactive") String status) { }
