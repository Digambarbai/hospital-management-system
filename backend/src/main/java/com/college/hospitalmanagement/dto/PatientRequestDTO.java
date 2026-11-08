package com.college.hospitalmanagement.dto;
import jakarta.validation.constraints.*; import java.time.LocalDate;
public record PatientRequestDTO(@NotBlank String fullName,@NotBlank String gender,@NotNull @Past LocalDate dateOfBirth,@NotBlank @Pattern(regexp="^[+0-9 ()-]{7,30}$") String mobile,@Email String email,@Pattern(regexp="^(A|B|AB|O)[+-]$",message="Enter a valid blood group") String bloodGroup,@Size(max=500) String address,@Size(max=200) String emergencyContact,@NotBlank String status){}
