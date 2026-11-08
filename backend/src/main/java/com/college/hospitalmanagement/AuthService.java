package com.college.hospitalmanagement;

import com.college.hospitalmanagement.dto.AuthResponseDTO;
import com.college.hospitalmanagement.dto.AdminPasswordChangeDTO;
import com.college.hospitalmanagement.dto.AdminProfileDTO;
import com.college.hospitalmanagement.dto.AdminProfileUpdateDTO;
import com.college.hospitalmanagement.dto.LoginRequestDTO;
import com.college.hospitalmanagement.dto.DoctorResponseDTO;
import com.college.hospitalmanagement.dto.DoctorProfileUpdateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
public class AuthService {
    private final AdminAccountRepository adminRepository;
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AdminAccountRepository adminRepository, DoctorRepository doctorRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Authentication authenticate(LoginRequestDTO request) {
        AdminAccount admin = adminRepository.findByEmailIgnoreCase(request.email()).orElse(null);
        if (admin != null) {
            if (!admin.isActive() || !passwordEncoder.matches(request.password(), admin.getPasswordHash())) throw invalidCredentials();
            return token(new SessionUser(admin.getId(), admin.getName(), admin.getEmail(), "ADMIN"));
        }

        Doctor doctor = doctorRepository.findByLoginEmailIgnoreCase(request.email()).orElse(null);
        if (doctor != null && doctor.getPasswordHash() != null
                && !"Inactive".equalsIgnoreCase(doctor.getStatus())
                && passwordEncoder.matches(request.password(), doctor.getPasswordHash())) {
            return token(new SessionUser(doctor.getId(), doctor.getFullName(), doctor.getLoginEmail(), "DOCTOR"));
        }

        throw invalidCredentials();
    }

    public AuthResponseDTO responseFor(SessionUser user) {
        return new AuthResponseDTO(user.id(), user.name(), user.email(), user.role());
    }

    public AdminProfileDTO profile(SessionUser user) { return toProfile(findAdmin(user.id())); }

    public DoctorResponseDTO doctorProfile(SessionUser user) {
        return doctorResponse(findDoctor(user.id()));
    }

    @Transactional
    public DoctorResponseDTO updateDoctorProfile(SessionUser user, DoctorProfileUpdateDTO request) {
        Doctor doctor = findDoctor(user.id());
        doctor.setFullName(request.fullName().trim());
        doctor.setMobile(request.mobile().trim());
        doctor.setEmail(request.email() == null ? null : request.email().trim());
        doctor.setQualification(request.qualification().trim());
        return doctorResponse(doctorRepository.saveAndFlush(doctor));
    }

    @Transactional
    public void changeDoctorPassword(SessionUser user, AdminPasswordChangeDTO request) {
        Doctor doctor = findDoctor(user.id());
        if (!passwordEncoder.matches(request.oldPassword(), doctor.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Old password is incorrect");
        }
        doctor.setPasswordHash(passwordEncoder.encode(request.newPassword()));
        doctorRepository.save(doctor);
    }

    private Doctor findDoctor(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Session is no longer valid"));
    }

    private DoctorResponseDTO doctorResponse(Doctor doctor) {
        return new DoctorResponseDTO(doctor.getId(), doctor.getFullName(), doctor.getGender(), doctor.getDateOfBirth(),
                doctor.getMobile(), doctor.getEmail(), doctor.getQualification(), doctor.getSpecialization(),
                doctor.getDepartment(), doctor.getYearsOfExperience(), doctor.getConsultationFee(), doctor.getStatus(),
                doctor.getLoginEmail());
    }

    @Transactional
    public AdminProfileDTO updateProfile(SessionUser user, AdminProfileUpdateDTO request) {
        AdminAccount admin = findAdmin(user.id());
        if (adminRepository.existsByEmailIgnoreCaseAndIdNot(request.email(), admin.getId())) {
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        admin.setEmpId(request.empId().trim());
        admin.setName(request.name().trim());
        admin.setEmail(request.email().trim());
        AdminAccount saved = adminRepository.saveAndFlush(admin);
        return toProfile(saved);
    }

    @Transactional
    public void changePassword(SessionUser user, AdminPasswordChangeDTO request) {
        AdminAccount admin = findAdmin(user.id());
        if (!passwordEncoder.matches(request.oldPassword(), admin.getPasswordHash())) {
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.BAD_REQUEST, "Old password is incorrect");
        }
        admin.setPasswordHash(passwordEncoder.encode(request.newPassword()));
        adminRepository.save(admin);
    }

    private AdminAccount findAdmin(Long id) {
        return adminRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Session is no longer valid"));
    }

    private AdminProfileDTO toProfile(AdminAccount admin) {
        return new AdminProfileDTO(admin.getId(), admin.getEmpId(), admin.getName(), admin.getEmail(), "ADMIN", admin.isActive());
    }

    private Authentication token(SessionUser user) {
        return new UsernamePasswordAuthenticationToken(user, null, AuthorityUtils.createAuthorityList("ROLE_" + user.role()));
    }

    public Authentication authenticationFor(SessionUser user) { return token(user); }

    private ResponseStatusException invalidCredentials() {
        return new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
    }
}
