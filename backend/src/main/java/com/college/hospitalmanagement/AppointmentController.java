package com.college.hospitalmanagement;

import com.college.hospitalmanagement.dto.*;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/appointments")
public class AppointmentController {
    private final AppointmentService service; public AppointmentController(AppointmentService service){this.service=service;}
    @GetMapping public List<AppointmentResponseDTO> list(@AuthenticationPrincipal SessionUser user){return service.list(user);}
    @GetMapping("/{id}") public AppointmentResponseDTO get(@PathVariable Long id,@AuthenticationPrincipal SessionUser user){return service.get(id,user);}
    @PostMapping public ResponseEntity<AppointmentResponseDTO> create(@Valid @RequestBody AppointmentRequestDTO request){AppointmentResponseDTO a=service.create(request);return ResponseEntity.created(URI.create("/api/appointments/"+a.id())).body(a);}
    @PutMapping("/{id}") public AppointmentResponseDTO update(@PathVariable Long id,@Valid @RequestBody AppointmentRequestDTO request){return service.update(id,request);}
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){service.delete(id);return ResponseEntity.noContent().build();}
}
