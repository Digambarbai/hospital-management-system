package com.college.hospitalmanagement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "nurses")
public class Nurse {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 150) private String fullName;
    @Column(nullable = false, length = 20) private String gender;
    private LocalDate dateOfBirth;
    @Column(nullable = false, length = 30) private String mobile;
    @Column(length = 150) private String email;
    @Column(nullable = false, length = 150) private String qualification;
    @Column(nullable = false, length = 100) private String department;
    @Column(nullable = false) private Integer yearsOfExperience;
    @Column(nullable = false, length = 20) private String shift;
    @Column(nullable = false) private LocalDate joiningDate;
    @Column(nullable = false, length = 500) private String address;
    @Column(nullable = false, length = 20) private String status;
    @Column(nullable = false, unique = true, length = 150) private String loginEmail;
    @JsonIgnore @Column(nullable = false, length = 100) private String passwordHash;
    public Long getId(){return id;} public String getFullName(){return fullName;} public String getGender(){return gender;} public LocalDate getDateOfBirth(){return dateOfBirth;} public String getMobile(){return mobile;} public String getEmail(){return email;} public String getQualification(){return qualification;} public String getDepartment(){return department;} public Integer getYearsOfExperience(){return yearsOfExperience;} public String getShift(){return shift;} public LocalDate getJoiningDate(){return joiningDate;} public String getAddress(){return address;} public String getStatus(){return status;} public String getLoginEmail(){return loginEmail;} public String getPasswordHash(){return passwordHash;}
    public void setFullName(String v){fullName=v;} public void setGender(String v){gender=v;} public void setDateOfBirth(LocalDate v){dateOfBirth=v;} public void setMobile(String v){mobile=v;} public void setEmail(String v){email=v;} public void setQualification(String v){qualification=v;} public void setDepartment(String v){department=v;} public void setYearsOfExperience(Integer v){yearsOfExperience=v;} public void setShift(String v){shift=v;} public void setJoiningDate(LocalDate v){joiningDate=v;} public void setAddress(String v){address=v;} public void setStatus(String v){status=v;} public void setLoginEmail(String v){loginEmail=v;} public void setPasswordHash(String v){passwordHash=v;}
}
