package com.college.hospitalmanagement;

import com.college.hospitalmanagement.dto.DoctorRequestDTO;
import com.college.hospitalmanagement.dto.DoctorPasswordResetDTO;
import com.college.hospitalmanagement.dto.DoctorResponseDTO;
import com.college.hospitalmanagement.validation.CreateValidation;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    public ResponseEntity<DoctorResponseDTO> create(
            @Validated({Default.class, CreateValidation.class}) @RequestBody DoctorRequestDTO request) {
        DoctorResponseDTO doctor = doctorService.create(request);
        return ResponseEntity.created(URI.create("/api/doctors/" + doctor.id())).body(doctor);
    }

    @GetMapping
    public List<DoctorResponseDTO> list() {
        return doctorService.list();
    }

    @GetMapping("/{id}")
    public DoctorResponseDTO get(@PathVariable Long id) {
        return doctorService.get(id);
    }

    @PutMapping("/{id}")
    public DoctorResponseDTO update(@PathVariable Long id, @Valid @RequestBody DoctorRequestDTO request) {
        return doctorService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        doctorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/password-reset")
    public ResponseEntity<Void> resetPassword(@PathVariable Long id, @Valid @RequestBody DoctorPasswordResetDTO request) {
        doctorService.resetPassword(id, request);
        return ResponseEntity.noContent().build();
    }
}
