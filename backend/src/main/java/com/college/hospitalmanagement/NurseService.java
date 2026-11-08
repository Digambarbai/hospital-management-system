package com.college.hospitalmanagement;
import com.college.hospitalmanagement.dto.*;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service @Transactional(readOnly=true)
public class NurseService {
 private final NurseRepository nurses; private final DoctorRepository doctors; private final AdminAccountRepository admins; private final PasswordEncoder encoder;
 public NurseService(NurseRepository nurses, DoctorRepository doctors, AdminAccountRepository admins, PasswordEncoder encoder){this.nurses=nurses;this.doctors=doctors;this.admins=admins;this.encoder=encoder;}
 public List<NurseResponseDTO> list(){return nurses.findAll().stream().map(this::response).toList();} public NurseResponseDTO get(Long id){return response(find(id));}
 @Transactional public NurseResponseDTO create(NurseRequestDTO r){requirePassword(r); ensureAvailable(r.loginEmail(),null); Nurse n=new Nurse(); apply(r,n); n.setPasswordHash(encoder.encode(r.password())); return response(nurses.save(n));}
 @Transactional public NurseResponseDTO update(Long id,NurseRequestDTO r){Nurse n=find(id);ensureAvailable(r.loginEmail(),id);apply(r,n);if(r.password()!=null&&!r.password().isBlank()){if(!r.password().equals(r.confirmPassword()))throw new IllegalArgumentException("Password and confirm password must match");n.setPasswordHash(encoder.encode(r.password()));}return response(nurses.save(n));}
 @Transactional public void delete(Long id){nurses.delete(find(id));}
 @Transactional public void resetPassword(Long id,DoctorPasswordResetDTO r){if(!r.newPassword().equals(r.confirmPassword()))throw new IllegalArgumentException("Password and confirm password must match");Nurse n=find(id);n.setPasswordHash(encoder.encode(r.newPassword()));nurses.save(n);}
 private void requirePassword(NurseRequestDTO r){if(r.password()==null||r.password().isBlank())throw new IllegalArgumentException("Password is required");if(!r.password().equals(r.confirmPassword()))throw new IllegalArgumentException("Password and confirm password must match");}
 private Nurse find(Long id){return nurses.findById(id).orElseThrow(()->new IllegalArgumentException("Nurse not found: "+id));}
 private void ensureAvailable(String email,Long id){String value=email.trim();nurses.findByLoginEmailIgnoreCase(value).filter(n->!n.getId().equals(id)).ifPresent(n->{throw new IllegalArgumentException("Login email already exists");});if(doctors.findByLoginEmailIgnoreCase(value).isPresent()||admins.findByEmailIgnoreCase(value).isPresent())throw new IllegalArgumentException("Login email already exists");}
 private void apply(NurseRequestDTO r,Nurse n){n.setFullName(r.fullName());n.setGender(r.gender());n.setDateOfBirth(r.dateOfBirth());n.setMobile(r.mobile());n.setEmail(r.email());n.setQualification(r.qualification());n.setDepartment(r.department());n.setYearsOfExperience(r.yearsOfExperience());n.setShift(r.shift());n.setJoiningDate(r.joiningDate());n.setAddress(r.address());n.setStatus(r.status());n.setLoginEmail(r.loginEmail());}
 private NurseResponseDTO response(Nurse n){return new NurseResponseDTO(n.getId(),n.getFullName(),n.getGender(),n.getDateOfBirth(),n.getMobile(),n.getEmail(),n.getQualification(),n.getDepartment(),n.getYearsOfExperience(),n.getShift(),n.getJoiningDate(),n.getAddress(),n.getStatus(),n.getLoginEmail());}
}
