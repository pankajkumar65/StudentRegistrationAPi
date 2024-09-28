package com.student.Registration.Repository;

import com.student.Registration.Entity.EnrollEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import com.student.Registration.Entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollRepository extends JpaRepository<EnrollEntity, Long> {
    List<EnrollEntity> findByUser(UserEntity user);
}
