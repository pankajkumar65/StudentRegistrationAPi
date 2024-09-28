package com.student.Registration.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@Setter
@Getter
public class UserEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @Column
    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @Column
    @NotBlank(message = "school name is mandatory")
    private String schoolName;

    @Column(unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = "Email must be a valid address and end with '.com'")
    @NotBlank(message = "email is mandatory")
    private String email;


    @Column
    @NotBlank(message = "class is mandatory")
    private String studentClass;

    @Column
    @NotBlank(message = "roll number is mandatory")
    private String rollNo;

    @Column
    @Pattern(regexp = "^\\d{10}$", message = "Mobile number should be a valid 10-digit number")
    private String mobileNo;

    @Column
    @NotBlank(message = "password is mandatory")
    private String password;

    @Column
    @NotBlank(message = "conform password is mandatory")
    private String conformPassword;

    @Column
    private Boolean isVerified;

    @Column
    private String otp;

    @Column
    private String role;

    // One-to-many relationship with addresses
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private List<addressEntity> address;


    public UserEntity() {
    }
}
