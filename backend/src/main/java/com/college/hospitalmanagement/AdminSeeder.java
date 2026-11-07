package com.college.hospitalmanagement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
public class AdminSeeder implements CommandLineRunner {
    private final AdminAccountRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.auth.seed-admin-email}")
    private String email;
    @Value("${app.auth.seed-admin-password}")
    private String password;
    @Value("${app.auth.seed-admin-name}")
    private String name;
    @Value("${app.auth.reset-admin-password}")
    private boolean resetPassword;

    public AdminSeeder(AdminAccountRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (!StringUtils.hasText(email) || !StringUtils.hasText(password)) return;
        Optional<AdminAccount> existing = adminRepository.findByEmailIgnoreCase(email);
        if (existing.isPresent()) {
            if (resetPassword) {
                AdminAccount admin = existing.get();
                admin.setPasswordHash(passwordEncoder.encode(password));
                adminRepository.save(admin);
            }
            return;
        }
        AdminAccount admin = new AdminAccount();
        admin.setName(name);
        admin.setEmail(email.trim());
        admin.setPasswordHash(passwordEncoder.encode(password));
        admin.setActive(true);
        adminRepository.save(admin);
    }
}
