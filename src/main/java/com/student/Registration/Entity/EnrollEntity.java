package com.student.Registration.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "enrollments")
public class EnrollEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity course;

    public EnrollEntity() {
    }

    public EnrollEntity(UserEntity user, CourseEntity course) {
        this.user = user;
        this.course = course;
    }
}
