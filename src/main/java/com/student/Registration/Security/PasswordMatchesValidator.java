package com.student.Registration.Security;

import com.student.Registration.Dto.studentRegistration;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, studentRegistration> {

    @Override
    public boolean isValid(studentRegistration studentDto, ConstraintValidatorContext context) {
        return studentDto.getPassword().equals(studentDto.getConfirmPassword());
    }
}