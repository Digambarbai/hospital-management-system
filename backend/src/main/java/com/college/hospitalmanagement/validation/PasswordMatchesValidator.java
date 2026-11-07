package com.college.hospitalmanagement.validation;

import com.college.hospitalmanagement.dto.DoctorRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, DoctorRequestDTO> {
    @Override
    public boolean isValid(DoctorRequestDTO value, ConstraintValidatorContext context) {
        return value == null || (value.password() == null && value.confirmPassword() == null)
            || (value.password() != null && value.password().equals(value.confirmPassword()));
    }
}
