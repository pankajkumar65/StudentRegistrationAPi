package com.student.Registration.controller;
import com.student.Registration.Entity.CourseEntity;
import com.student.Registration.Services.Admin;
import com.student.Registration.Services.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private Admin courseService;

    @Autowired
    private User userService;


    @PostMapping("/add")
    public ResponseEntity<?> createCourse(@RequestBody CourseEntity course) {
        try {
            CourseEntity savedCourse = courseService.createCourse(course);
            return ResponseEntity.ok("Course added successfully!");
        } catch (IllegalArgumentException e) {
            // Return a response with a custom message for duplicate course name
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<CourseEntity>> getAllCourses() {
        List<CourseEntity> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseEntity> getCourseById(@PathVariable Long id) {
        CourseEntity course = courseService.getCourseById(id);
        return course != null ? ResponseEntity.ok(course) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{courseId}/enroll/{userId}")
    public ResponseEntity<String> enrollStudent(
            @PathVariable Long courseId,
            @PathVariable int userId) {

        try {
            courseService.enrollUserInCourse(courseId, userId);
            return ResponseEntity.ok("User enrolled successfully in the course!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public List<CourseEntity> searchCourses(
            @RequestParam(required = false) String courseName,
            @RequestParam(required = false) String duration,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String tags,
            @RequestParam(required = false) String teacherName,
            @RequestParam(required = false) Double fee,
            @RequestParam(required = false) Integer enrolledStudents) {

        return courseService.searchCourses(courseName, duration, description, tags, teacherName, fee, enrolledStudents);
    }

    // New endpoint to fetch all courses for a user
    @GetMapping("/user/{userId}/enrollments")
    public ResponseEntity<List<CourseEntity>> getCoursesForUser(
            @PathVariable int userId) {

        try {
            List<CourseEntity> courses = userService.getCoursesForUser(userId);
            return ResponseEntity.ok(courses);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


}
