package com.college.hospitalmanagement;

import com.college.hospitalmanagement.dto.*;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/departments")
public class DepartmentController {
    private final DepartmentService service; public DepartmentController(DepartmentService service){this.service=service;}
    @GetMapping public List<DepartmentResponseDTO> list(){return service.list();}
    @GetMapping("/{id}") public DepartmentResponseDTO get(@PathVariable Long id){return service.get(id);}
    @PostMapping public ResponseEntity<DepartmentResponseDTO> create(@Valid @RequestBody DepartmentRequestDTO request){DepartmentResponseDTO d=service.create(request);return ResponseEntity.created(URI.create("/api/departments/"+d.id())).body(d);}
    @PutMapping("/{id}") public DepartmentResponseDTO update(@PathVariable Long id,@Valid @RequestBody DepartmentRequestDTO request){return service.update(id,request);}
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){service.delete(id);return ResponseEntity.noContent().build();}
}
