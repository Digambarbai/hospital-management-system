package com.college.hospitalmanagement;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validationError(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .orElse("Invalid request");
        return ResponseEntity.badRequest().body(Map.of("message", message));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> doctorError(IllegalArgumentException exception) {
        String message = exception.getMessage();
        HttpStatus status = message != null && (message.contains("already exists") || message.contains("already has") || message.contains("cannot be deleted") || message.contains("capacity") || message.contains("Only available") || message.contains("already has an active") || message.contains("allocation history"))
                ? HttpStatus.CONFLICT
                : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(Map.of("message", message == null ? "Request failed" : message));
    }
}
