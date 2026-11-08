package com.college.hospitalmanagement.dto;

import java.util.List;
public record DepartmentResponseDTO(Long id, String name, String code, String description, Long headDoctorId,
        String headDoctorName, List<AssignedStaffDTO> assignedDoctors, List<AssignedStaffDTO> assignedNurses,
        String status) { }
