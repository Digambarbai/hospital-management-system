package com.college.hospitalmanagement;
import java.util.Optional; import org.springframework.data.jpa.repository.JpaRepository;
public interface PatientRepository extends JpaRepository<Patient,Long>{Optional<Patient> findByMobile(String mobile); Optional<Patient> findByEmailIgnoreCase(String email);}
