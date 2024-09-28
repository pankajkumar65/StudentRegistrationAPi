package com.student.Registration.Services;

import com.student.Registration.Dto.studentRegistration;
import com.student.Registration.Dto.addressDto;
import com.student.Registration.Entity.CourseEntity;
import com.student.Registration.Entity.EnrollEntity;
import com.student.Registration.Entity.UserEntity;
import com.student.Registration.Entity.addressEntity;
import com.student.Registration.Repository.AddressRepository;
import com.student.Registration.Repository.EnrollRepository;
import com.student.Registration.Specification.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.student.Registration.Repository.UserRepository;
import org.springframework.data.jpa.domain.Specification;
import com.student.Registration.Specification.AddressSpecification;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class User {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;  // Inject JavaMailSender to send emails

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private EnrollRepository enrollRepository;

    private String currentOtp;  // Store the current OTP temporarily

    // Save a new student and send OTP
    public void saveStudent(studentRegistration studentDto) {
        // Check if the email ends with '.com' (in case validation is bypassed)
//        if (!studentDto.getEmail().toLowerCase().endsWith(".com")) {
//            throw new IllegalArgumentException("Please enter valid Email!");
//        }
        // Create user entity
        UserEntity student = new UserEntity();
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setSchoolName(studentDto.getSchoolName());
        student.setEmail(studentDto.getEmail());
        student.setStudentClass(studentDto.getStudentClass());
        student.setRollNo(studentDto.getRollNo());
        student.setMobileNo(studentDto.getMobileNo());
        student.setPassword(studentDto.getPassword());
        student.setRole(studentDto.getRole());
        student.setConformPassword(studentDto.getConfirmPassword());

        // Map the address DTO to the addressEntity
        List<addressEntity> addressList = new ArrayList<>();
        for (addressDto addressDto : studentDto.getAddress()) {
            addressEntity address = new addressEntity();
            address.setStreet(addressDto.getStreet());
            address.setCity(addressDto.getCity());
            address.setState(addressDto.getState());
            address.setZipcode(addressDto.getZipCode());
            address.setCountry(addressDto.getCountry());
            addressList.add(address);
        }
        // Encrypt the password
        student.setPassword(passwordEncoder.encode(studentDto.getPassword()));
        student.setConformPassword(passwordEncoder.encode(studentDto.getConfirmPassword()));

        // Set address in the student entity
        student.setAddress(addressList);

        // Send OTP via email (if applicable)
        String otp = generateOtp();
        student.setOtp(otp);
        sendOtpEmail(student.getEmail(), otp);

        // Save student with addresses
        userRepository.save(student);
    }

    // Generate a 6-digit OTP
    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);  // 6-digit OTP
        return String.valueOf(otp);
    }

    // Send the OTP to the student's email
    private void sendOtpEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp);

        mailSender.send(message);
    }

    // Get all students
    public List<UserEntity> getAllStudents() {
        return userRepository.findAll();
    }

    // Get student by ID
    public UserEntity getStudentById(int id) {
        Optional<UserEntity> optionalStudent = userRepository.findById(id);
        return optionalStudent.orElse(null);  // Return null if student is not found
    }

    // Delete a student by ID
    public void deleteStudent(int id) {
        userRepository.deleteById(id);
    }

    // Verify the OTP
    public boolean verifyOtp(String otp, String email) {
        // Find the student by email
        UserEntity student = userRepository.findByEmail(email);

        if (student != null && student.getOtp().equals(otp)) {
            // Mark the student as verified
            student.setIsVerified(true);
            userRepository.save(student);  // Save updated student status
            return true;
        }
        return false;
    }

    // Authenticate user with verification check
    public boolean authenticateUser(String email, String password) {
        UserEntity user = userRepository.findByEmail(email);

        if (user != null) {
            if (!user.getIsVerified()) {
                // User has not verified OTP, return false and handle in controller
                throw new RuntimeException("Please verify your OTP before logging in.");
            }

            // Compare the hashed password with the input password
            return passwordEncoder.matches(password, user.getPassword());
        }

        return false; // User not found
    }


    public List<UserEntity> searchUsers(String firstName, String lastName, String email, String schoolName,
                                        String studentClass, String rollNo, String mobileNo, Boolean isVerified) {
        Specification<UserEntity> spec = Specification.where(null);

        if (firstName != null && !firstName.isEmpty()) {
            spec = spec.and(UserSpecification.hasFirstName(firstName));
        }
        if (lastName != null && !lastName.isEmpty()) {
            spec = spec.and(UserSpecification.hasLastName(lastName));
        }
        if (email != null && !email.isEmpty()) {
            spec = spec.and(UserSpecification.hasEmail(email));
        }
        if (schoolName != null && !schoolName.isEmpty()) {
            spec = spec.and(UserSpecification.hasSchoolName(schoolName));
        }
        if (studentClass != null && !studentClass.isEmpty()) {
            spec = spec.and(UserSpecification.hasClass(studentClass));
        }
        if (rollNo != null && !rollNo.isEmpty()) {
            spec = spec.and(UserSpecification.hasRollNo(rollNo));
        }
        if (mobileNo != null && !mobileNo.isEmpty()) {
            spec = spec.and(UserSpecification.hasMobileNo(mobileNo));
        }
        if (isVerified != null) {
            spec = spec.and(UserSpecification.isVerified(isVerified));
        }

        return userRepository.findAll(spec);
    }

    public List<addressEntity> searchAddresses(String street, String city, String state, String country, String zipCode) {
        Specification<addressEntity> spec = Specification.where(null);

        if (street != null && !street.isEmpty()) {
            spec = spec.and(AddressSpecification.hasStreet(street));
        }
        if (city != null && !city.isEmpty()) {
            spec = spec.and(AddressSpecification.hasCity(city));
        }
        if (state != null && !state.isEmpty()) {
            spec = spec.and(AddressSpecification.hasState(state));
        }
        if (country != null && !country.isEmpty()) {
            spec = spec.and(AddressSpecification.hasCountry(country));
        }
        if (zipCode != null && !zipCode.isEmpty()) {
            spec = spec.and(AddressSpecification.hasZipCode(zipCode));
        }

        return addressRepository.findAll(spec);
    }

    public List<CourseEntity> getCoursesForUser(int userId) {
        UserEntity user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        // Fetch all enrollments for the user and map them to course entities
        List<EnrollEntity> enrollments = enrollRepository.findByUser(user);
        return enrollments.stream()
                .map(EnrollEntity::getCourse)
                .collect(Collectors.toList());
    }
}
