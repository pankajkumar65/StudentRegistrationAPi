package com.student.Registration.Repository;

import com.student.Registration.Entity.CourseEntity;
import com.student.Registration.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CourseRepository extends JpaRepository<CourseEntity, Long>, JpaSpecificationExecutor<CourseEntity> {
    boolean existsByCourseName(String courseName);

}
