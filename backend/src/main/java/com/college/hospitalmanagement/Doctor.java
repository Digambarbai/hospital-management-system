package com.college.hospitalmanagement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "doctors")
public class Doctor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 150) private String fullName;
    @Column(nullable = false, length = 20) private String gender;
    private LocalDate dateOfBirth;
    @Column(nullable = false, length = 30) private String mobile;
    @Column(length = 150) private String email;
    @Column(nullable = false, length = 150) private String qualification;
    @Column(nullable = false, length = 100) private String specialization;
    @Column(nullable = false, length = 100) private String department;
    @Column(nullable = false) private Integer yearsOfExperience;
    @Column(precision = 10, scale = 2) private BigDecimal consultationFee;
    @Column(nullable = false, length = 20) private String status;
    @Column(nullable = false, unique = true, length = 150) private String loginEmail;
    @JsonIgnore @Column(nullable = false, length = 100) private String passwordHash;
    public Long getId(){return id;} public String getFullName(){return fullName;} public String getGender(){return gender;} public LocalDate getDateOfBirth(){return dateOfBirth;} public String getMobile(){return mobile;} public String getEmail(){return email;} public String getQualification(){return qualification;} public String getSpecialization(){return specialization;} public String getDepartment(){return department;} public Integer getYearsOfExperience(){return yearsOfExperience;} public BigDecimal getConsultationFee(){return consultationFee;} public String getStatus(){return status;} public String getLoginEmail(){return loginEmail;} public String getPasswordHash(){return passwordHash;}
    public void setId(Long v){id=v;} public void setFullName(String v){fullName=v;} public void setGender(String v){gender=v;} public void setDateOfBirth(LocalDate v){dateOfBirth=v;} public void setMobile(String v){mobile=v;} public void setEmail(String v){email=v;} public void setQualification(String v){qualification=v;} public void setSpecialization(String v){specialization=v;} public void setDepartment(String v){department=v;} public void setYearsOfExperience(Integer v){yearsOfExperience=v;} public void setConsultationFee(BigDecimal v){consultationFee=v;} public void setStatus(String v){status=v;} public void setLoginEmail(String v){loginEmail=v;} public void setPasswordHash(String v){passwordHash=v;}
}
