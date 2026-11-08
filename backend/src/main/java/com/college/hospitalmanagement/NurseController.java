package com.college.hospitalmanagement;
import com.college.hospitalmanagement.dto.*;
import jakarta.validation.Valid;
import java.net.URI; import java.util.List;
import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/nurses")
public class NurseController {
 private final NurseService service; public NurseController(NurseService service){this.service=service;}
 @PostMapping public ResponseEntity<NurseResponseDTO> create(@Valid @RequestBody NurseRequestDTO r){NurseResponseDTO n=service.create(r);return ResponseEntity.created(URI.create("/api/nurses/"+n.id())).body(n);}
 @GetMapping public List<NurseResponseDTO> list(){return service.list();} @GetMapping("/{id}") public NurseResponseDTO get(@PathVariable Long id){return service.get(id);}
 @PutMapping("/{id}") public NurseResponseDTO update(@PathVariable Long id,@Valid @RequestBody NurseRequestDTO r){return service.update(id,r);}
 @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){service.delete(id);return ResponseEntity.noContent().build();}
 @PostMapping("/{id}/password-reset") public ResponseEntity<Void> reset(@PathVariable Long id,@Valid @RequestBody DoctorPasswordResetDTO r){service.resetPassword(id,r);return ResponseEntity.noContent().build();}
}
