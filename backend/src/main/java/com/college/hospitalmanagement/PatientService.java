package com.college.hospitalmanagement;
import com.college.hospitalmanagement.dto.*; import java.time.LocalDate; import java.util.List; import java.util.UUID; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
@Service @Transactional(readOnly=true) public class PatientService{
 private final PatientRepository repo; public PatientService(PatientRepository repo){this.repo=repo;}
 public List<PatientResponseDTO> list(){return repo.findAll().stream().map(this::response).toList();} public PatientResponseDTO get(Long id){return response(find(id));}
 @Transactional public PatientResponseDTO create(PatientRequestDTO r){ensureUnique(r,null);Patient p=new Patient();apply(r,p);p.setRegistrationDate(LocalDate.now());p.setPatientCode("TMP-"+UUID.randomUUID().toString().replace("-","").substring(0,16));p=repo.saveAndFlush(p);p.setPatientCode(String.format("PT-%06d",p.getId()));return response(repo.save(p));}
 @Transactional public PatientResponseDTO update(Long id,PatientRequestDTO r){Patient p=find(id);ensureUnique(r,id);apply(r,p);return response(repo.save(p));}
 @Transactional public void delete(Long id){repo.delete(find(id));}
 private Patient find(Long id){return repo.findById(id).orElseThrow(()->new IllegalArgumentException("Patient not found: "+id));}
 private void ensureUnique(PatientRequestDTO r,Long id){repo.findByMobile(r.mobile()).filter(p->!p.getId().equals(id)).ifPresent(p->{throw new IllegalArgumentException("Mobile number already exists");});if(r.email()!=null&&!r.email().isBlank())repo.findByEmailIgnoreCase(r.email()).filter(p->!p.getId().equals(id)).ifPresent(p->{throw new IllegalArgumentException("Email already exists");});}
 private void apply(PatientRequestDTO r,Patient p){p.setFullName(r.fullName().trim());p.setGender(r.gender());p.setDateOfBirth(r.dateOfBirth());p.setMobile(r.mobile().trim());p.setEmail(r.email()==null?null:r.email().trim());p.setBloodGroup(r.bloodGroup());p.setAddress(r.address());p.setEmergencyContact(r.emergencyContact());p.setStatus(r.status());}
 private PatientResponseDTO response(Patient p){return new PatientResponseDTO(p.getId(),p.getPatientCode(),p.getFullName(),p.getGender(),p.getDateOfBirth(),p.getMobile(),p.getEmail(),p.getBloodGroup(),p.getAddress(),p.getEmergencyContact(),p.getRegistrationDate(),p.getStatus());}
}
