package com.student.Registration.Services;

import com.student.Registration.Entity.CourseEntity;
import com.student.Registration.Repository.EnrollRepository;
import com.student.Registration.Repository.UserRepository;
import com.student.Registration.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.student.Registration.Specification.CourseSpecification;
import com.student.Registration.Specification.UserSpecification;
import com.student.Registration.Entity.UserEntity;
import com.student.Registration.Entity.EnrollEntity;


import java.util.List;

@Service
public class Admin {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnrollRepository enrollRepository;


    public CourseEntity createCourse(CourseEntity course) {
        // Check if the course name already exists
        if (courseRepository.existsByCourseName(course.getCourseName())) {
            throw new IllegalArgumentException("Course name must be unique!");
        }

        // Set the enrolledStudentsCount to 0 when a new course is created
        course.setEnrolledStudentsCount(0);
        return courseRepository.save(course);
    }


    public List<CourseEntity> getAllCourses() {
        return courseRepository.findAll();
    }

    public CourseEntity getCourseById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    // You can add a method to increase the enrolled students count
    public void increaseEnrolledStudentsCount(Long courseId) {
        CourseEntity course = getCourseById(courseId);
        if (course != null) {
            course.setEnrolledStudentsCount(course.getEnrolledStudentsCount() + 1);
            courseRepository.save(course);
        }
    }

    public List<CourseEntity> searchCourses(String courseName, String duration, String description,
                                            String tags, String teacherName, Double fee, Integer enrolledStudents) {
        Specification<CourseEntity> spec = Specification.where(null);

        if (courseName != null && !courseName.isEmpty()) {
            spec = spec.and(CourseSpecification.hasCourseName(courseName));
        }
        if (duration != null && !duration.isEmpty()) {
            spec = spec.and(CourseSpecification.hasDuration(duration));
        }
        if (description != null && !description.isEmpty()) {
            spec = spec.and(CourseSpecification.hasDescription(description));
        }
        if (tags != null && !tags.isEmpty()) {
            spec = spec.and(CourseSpecification.hasTags(tags));
        }
        if (teacherName != null && !teacherName.isEmpty()) {
            spec = spec.and(CourseSpecification.hasTeacherName(teacherName));
        }
        if (fee != null) {
            spec = spec.and(CourseSpecification.hasFee(fee));
        }
        if (enrolledStudents != null) {
            spec = spec.and(CourseSpecification.hasEnrolledStudents(enrolledStudents));
        }

        return courseRepository.findAll(spec);
    }



    public UserEntity getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public void enrollUserInCourse(Long courseId, int userId) {
        CourseEntity course = getCourseById(courseId);
        UserEntity user = getUserById(userId);

        if (course != null && user != null) {
            // Create enrollment record
            EnrollEntity enrollEntity = new EnrollEntity(user, course);
            enrollRepository.save(enrollEntity);

            // Increase enrolled student count
            course.setEnrolledStudentsCount(course.getEnrolledStudentsCount() + 1);
            courseRepository.save(course);
        } else {
            throw new IllegalArgumentException("Course or User not found");
        }
    }

}
