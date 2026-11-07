package com.college.hospitalmanagement;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByLoginEmailIgnoreCase(String loginEmail);
    boolean existsByLoginEmailIgnoreCase(String loginEmail);
}
