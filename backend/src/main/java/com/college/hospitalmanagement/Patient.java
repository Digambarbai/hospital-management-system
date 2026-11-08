package com.college.hospitalmanagement;
import jakarta.persistence.*;
import java.time.LocalDate;
@Entity @Table(name="patients")
public class Patient {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true,length=20) private String patientCode;
 @Column(nullable=false,length=150) private String fullName; @Column(nullable=false,length=20) private String gender; @Column(nullable=false) private LocalDate dateOfBirth;
 @Column(nullable=false,unique=true,length=30) private String mobile; @Column(length=150) private String email; @Column(length=5) private String bloodGroup;
 @Column(length=500) private String address; @Column(length=200) private String emergencyContact; @Column(nullable=false) private LocalDate registrationDate; @Column(nullable=false,length=20) private String status;
 public Long getId(){return id;} public String getPatientCode(){return patientCode;} public String getFullName(){return fullName;} public String getGender(){return gender;} public LocalDate getDateOfBirth(){return dateOfBirth;} public String getMobile(){return mobile;} public String getEmail(){return email;} public String getBloodGroup(){return bloodGroup;} public String getAddress(){return address;} public String getEmergencyContact(){return emergencyContact;} public LocalDate getRegistrationDate(){return registrationDate;} public String getStatus(){return status;}
 public void setPatientCode(String v){patientCode=v;} public void setFullName(String v){fullName=v;} public void setGender(String v){gender=v;} public void setDateOfBirth(LocalDate v){dateOfBirth=v;} public void setMobile(String v){mobile=v;} public void setEmail(String v){email=v;} public void setBloodGroup(String v){bloodGroup=v;} public void setAddress(String v){address=v;} public void setEmergencyContact(String v){emergencyContact=v;} public void setRegistrationDate(LocalDate v){registrationDate=v;} public void setStatus(String v){status=v;}
}
