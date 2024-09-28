package com.student.Registration.Dto;

import com.student.Registration.Security.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@PasswordMatches(message = "Password and confirm password must be the same")
public class studentRegistration {

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @NotBlank(message = "School name is mandatory")
    private String schoolName;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = "please enter valid Email")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Class is mandatory")
    private String studentClass;

    @Pattern(regexp = "^\\d{10}$", message = "Mobile number should be a valid 10-digit number")
    private String mobileNo;

    @NotBlank(message = "Roll number is mandatory")
    private String rollNo;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @NotBlank(message = "Confirm password is mandatory")
    private String confirmPassword;

    @NotBlank(message = "role is mandatory")
    private String role;

    @Valid
    private List<addressDto> address;

}

