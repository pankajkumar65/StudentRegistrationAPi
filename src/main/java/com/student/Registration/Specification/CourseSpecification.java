package com.student.Registration.Specification;

import com.student.Registration.Entity.CourseEntity;
import org.springframework.data.jpa.domain.Specification;

public class CourseSpecification {

    public static Specification<CourseEntity> hasCourseName(String courseName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("courseName"), courseName);
    }

    public static Specification<CourseEntity> hasDuration(String duration) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("duration"), duration);
    }

    public static Specification<CourseEntity> hasDescription(String description) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("description"), "%" + description + "%");
    }

    public static Specification<CourseEntity> hasTags(String tags) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("tags"), "%" + tags + "%");
    }

    public static Specification<CourseEntity> hasTeacherName(String teacherName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("teacherName"), teacherName);
    }

    public static Specification<CourseEntity> hasFee(Double fee) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("fee"), fee);
    }

    public static Specification<CourseEntity> hasEnrolledStudents(int enrolledStudents) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("enrolledStudents"), enrolledStudents);
    }
}
