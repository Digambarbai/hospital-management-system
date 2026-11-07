package com.college.hospitalmanagement;

import com.college.hospitalmanagement.dto.DoctorRequestDTO;
import com.college.hospitalmanagement.dto.DoctorPasswordResetDTO;
import com.college.hospitalmanagement.dto.DoctorResponseDTO;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class DoctorService {
    private final DoctorRepository repository;
    private final PasswordEncoder passwordEncoder;

    public DoctorService(DoctorRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<DoctorResponseDTO> list() { return repository.findAll().stream().map(this::toResponse).toList(); }
    public DoctorResponseDTO get(Long id) { return toResponse(find(id)); }

    @Transactional
    public DoctorResponseDTO create(DoctorRequestDTO request) {
        ensureEmailAvailable(request.loginEmail(), null);
        Doctor doctor = new Doctor();
        apply(request, doctor);
        doctor.setPasswordHash(passwordEncoder.encode(request.password()));
        return toResponse(repository.save(doctor));
    }

    @Transactional
    public DoctorResponseDTO update(Long id, DoctorRequestDTO request) {
        Doctor doctor = find(id);
        ensureEmailAvailable(request.loginEmail(), id);
        apply(request, doctor);
        if (request.password() != null && !request.password().isBlank()) doctor.setPasswordHash(passwordEncoder.encode(request.password()));
        return toResponse(repository.save(doctor));
    }

    @Transactional
    public void delete(Long id) { repository.delete(find(id)); }

    @Transactional
    public void resetPassword(Long id, DoctorPasswordResetDTO request) {
        if (!request.newPassword().equals(request.confirmPassword())) {
            throw new IllegalArgumentException("Password and confirm password must match");
        }
        Doctor doctor = find(id);
        doctor.setPasswordHash(passwordEncoder.encode(request.newPassword()));
        repository.save(doctor);
    }

    private Doctor find(Long id) { return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Doctor not found: " + id)); }
    private void ensureEmailAvailable(String email, Long currentId) { repository.findByLoginEmailIgnoreCase(email).filter(d -> !d.getId().equals(currentId)).ifPresent(d -> { throw new IllegalArgumentException("Login email already exists"); }); }
    private void apply(DoctorRequestDTO r, Doctor d) { d.setFullName(r.fullName()); d.setGender(r.gender()); d.setDateOfBirth(r.dateOfBirth()); d.setMobile(r.mobile()); d.setEmail(r.email()); d.setQualification(r.qualification()); d.setSpecialization(r.specialization()); d.setDepartment(r.department()); d.setYearsOfExperience(r.yearsOfExperience()); d.setConsultationFee(r.consultationFee()); d.setStatus(r.status()); d.setLoginEmail(r.loginEmail()); }
    private DoctorResponseDTO toResponse(Doctor d) { return new DoctorResponseDTO(d.getId(),d.getFullName(),d.getGender(),d.getDateOfBirth(),d.getMobile(),d.getEmail(),d.getQualification(),d.getSpecialization(),d.getDepartment(),d.getYearsOfExperience(),d.getConsultationFee(),d.getStatus(),d.getLoginEmail()); }
}
