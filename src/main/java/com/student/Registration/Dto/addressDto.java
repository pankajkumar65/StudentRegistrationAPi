package com.student.Registration.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class addressDto{

    @NotBlank(message = "Street is mandatory")
    private String street;

    @NotBlank(message = "City is mandatory")
    private String city;

    @NotBlank(message = "State is mandatory")
    private String state;

    @Pattern(regexp = "^\\d{5}$", message = "Zip code should be a 5-digit number")
    private String zipCode;

    @NotBlank(message = "Country is mandatory")
    private String country;

}
