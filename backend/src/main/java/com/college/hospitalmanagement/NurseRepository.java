package com.college.hospitalmanagement;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
public interface NurseRepository extends JpaRepository<Nurse, Long> { Optional<Nurse> findByLoginEmailIgnoreCase(String loginEmail); }
