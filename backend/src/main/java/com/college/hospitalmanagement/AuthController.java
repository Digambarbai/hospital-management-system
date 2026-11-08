package com.college.hospitalmanagement;

import com.college.hospitalmanagement.dto.AuthResponseDTO;
import com.college.hospitalmanagement.dto.LoginRequestDTO;
import com.college.hospitalmanagement.dto.AdminPasswordChangeDTO;
import com.college.hospitalmanagement.dto.AdminProfileDTO;
import com.college.hospitalmanagement.dto.AdminProfileUpdateDTO;
import com.college.hospitalmanagement.dto.DoctorResponseDTO;
import com.college.hospitalmanagement.dto.DoctorProfileUpdateDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final SecurityContextRepository securityContextRepository;

    public AuthController(AuthService authService, SecurityContextRepository securityContextRepository) {
        this.authService = authService;
        this.securityContextRepository = securityContextRepository;
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@Valid @RequestBody LoginRequestDTO request, HttpServletRequest httpRequest, HttpServletResponse response, CsrfToken csrfToken) {
        Authentication authentication = authService.authenticate(request);
        httpRequest.getSession(true);
        httpRequest.changeSessionId();
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, httpRequest, response);
        response.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());
        return authService.responseFor((SessionUser) authentication.getPrincipal());
    }

    @GetMapping("/me")
    public AuthResponseDTO me(Authentication authentication, HttpServletResponse response, CsrfToken csrfToken) {
        response.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());
        return authService.responseFor((SessionUser) authentication.getPrincipal());
    }

    @GetMapping("/profile")
    public AdminProfileDTO profile(Authentication authentication) {
        return authService.profile((SessionUser) authentication.getPrincipal());
    }

    @GetMapping("/doctor-profile")
    public DoctorResponseDTO doctorProfile(Authentication authentication) {
        return authService.doctorProfile((SessionUser) authentication.getPrincipal());
    }

    @PutMapping("/doctor-profile")
    public DoctorResponseDTO updateDoctorProfile(@Valid @RequestBody DoctorProfileUpdateDTO request, Authentication authentication) {
        return authService.updateDoctorProfile((SessionUser) authentication.getPrincipal(), request);
    }

    @PostMapping("/doctor-change-password")
    public ResponseEntity<Void> changeDoctorPassword(@Valid @RequestBody AdminPasswordChangeDTO request, Authentication authentication) {
        authService.changeDoctorPassword((SessionUser) authentication.getPrincipal(), request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/profile")
    public AdminProfileDTO updateProfile(@Valid @RequestBody AdminProfileUpdateDTO request, Authentication authentication, HttpServletRequest httpRequest, HttpServletResponse response) {
        AdminProfileDTO updated = authService.updateProfile((SessionUser) authentication.getPrincipal(), request);
        saveSessionUser(new SessionUser(updated.id(), updated.name(), updated.email(), updated.role()), httpRequest, response);
        return updated;
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody AdminPasswordChangeDTO request, Authentication authentication) {
        authService.changePassword((SessionUser) authentication.getPrincipal(), request);
        return ResponseEntity.noContent().build();
    }

    private void saveSessionUser(SessionUser user, HttpServletRequest request, HttpServletResponse response) {
        Authentication updatedAuthentication = authService.authenticationFor(user);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(updatedAuthentication);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, request, response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextHolder.clearContext();
        securityContextRepository.saveContext(SecurityContextHolder.createEmptyContext(), request, response);
        if (request.getSession(false) != null) request.getSession(false).invalidate();
        return ResponseEntity.noContent().build();
    }
}
