package com.student.Registration.controller;
import com.student.Registration.Dto.LoginRequest;
import com.student.Registration.Dto.OtpVerificationRequest;
import com.student.Registration.Dto.studentRegistration;
import com.student.Registration.Entity.UserEntity;
import com.student.Registration.Entity.addressEntity;
import com.student.Registration.Services.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private User studentService;

    // Create a new student and send OTP
    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<String> registerStudent(@Validated @RequestBody studentRegistration studentDto) {
        try {
            studentService.saveStudent(studentDto);
            return ResponseEntity.ok("Student registered successfully! OTP sent to email.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    // Verify OTP
    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody OtpVerificationRequest request) {
        boolean isVerified = studentService.verifyOtp(request.getOtp(), request.getEmail());
        if (isVerified) {
            return ResponseEntity.ok("OTP verified successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP!");
        }
    }


    // Get all students
    @GetMapping("/list")
    public ResponseEntity<List<UserEntity>> getAllStudents() {
        List<UserEntity> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    // Get a student by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getStudentById(@PathVariable int id) {
        UserEntity student = studentService.getStudentById(id);
        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            boolean isAuthenticated = studentService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());
            if (isAuthenticated) {
                return ResponseEntity.ok("Login successful!");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password!");
            }
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please verify your OTP before logging in!");
        }
    }


    @GetMapping("user/search")
    public List<UserEntity> searchUsers(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String schoolName,
            @RequestParam(required = false) String studentClass,
            @RequestParam(required = false) String rollNo,
            @RequestParam(required = false) String mobileNo,
            @RequestParam(required = false) Boolean isVerified) {

        return studentService.searchUsers(firstName, lastName, email, schoolName, studentClass, rollNo, mobileNo, isVerified);
    }

    @GetMapping("Address/search")
    public List<addressEntity> searchAddresses(
            @RequestParam(required = false) String street,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String zipCode) {

        return studentService.searchAddresses(street, city, state, country, zipCode);
    }

}
