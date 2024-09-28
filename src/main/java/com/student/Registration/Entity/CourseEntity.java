package com.student.Registration.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "Course name is mandatory")
    private String courseName;

    @NotBlank(message = "Duration is mandatory")
    private String duration;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotBlank(message = "Tags are mandatory")
    private String tags;

    @NotBlank(message = "Teacher name is mandatory")
    private String teacherName;

    private Double fee;

    // Changed this field to be an integer count of enrolled students
    @Column(name = "enrolled_students")
    private Integer enrolledStudentsCount;


}
