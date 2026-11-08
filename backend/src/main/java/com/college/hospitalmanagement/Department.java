package com.college.hospitalmanagement;

import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "departments")
public class Department {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, unique = true, length = 150) private String name;
    @Column(nullable = false, unique = true, length = 30) private String code;
    @Column(length = 1000) private String description;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "head_doctor_id") private Doctor headDoctor;
    @ManyToMany @JoinTable(name = "department_doctors", joinColumns = @JoinColumn(name = "department_id"), inverseJoinColumns = @JoinColumn(name = "doctor_id")) private Set<Doctor> assignedDoctors = new LinkedHashSet<>();
    @ManyToMany @JoinTable(name = "department_nurses", joinColumns = @JoinColumn(name = "department_id"), inverseJoinColumns = @JoinColumn(name = "nurse_id")) private Set<Nurse> assignedNurses = new LinkedHashSet<>();
    @Column(nullable = false, length = 20) private String status;
    public Long getId(){return id;} public String getName(){return name;} public String getCode(){return code;} public String getDescription(){return description;} public Doctor getHeadDoctor(){return headDoctor;} public Set<Doctor> getAssignedDoctors(){return assignedDoctors;} public Set<Nurse> getAssignedNurses(){return assignedNurses;} public String getStatus(){return status;}
    public void setName(String v){name=v;} public void setCode(String v){code=v;} public void setDescription(String v){description=v;} public void setHeadDoctor(Doctor v){headDoctor=v;} public void setAssignedDoctors(Set<Doctor> v){assignedDoctors=v;} public void setAssignedNurses(Set<Nurse> v){assignedNurses=v;} public void setStatus(String v){status=v;}
}
