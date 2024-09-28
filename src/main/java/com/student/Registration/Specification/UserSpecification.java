package com.student.Registration.Specification;

import com.student.Registration.Entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<UserEntity> hasFirstName(String firstName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("firstName"), firstName);
    }

    public static Specification<UserEntity> hasLastName(String lastName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("lastName"), lastName);
    }

    public static Specification<UserEntity> hasEmail(String email) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("email"), email);
    }

    public static Specification<UserEntity> hasSchoolName(String schoolName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("schoolName"), schoolName);
    }

    public static Specification<UserEntity> hasClass(String studentClass) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("studentClass"), studentClass);
    }

    public static Specification<UserEntity> hasRollNo(String rollNo) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("rollNo"), rollNo);
    }

    public static Specification<UserEntity> hasMobileNo(String mobileNo) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("mobileNo"), mobileNo);
    }

    public static Specification<UserEntity> isVerified(Boolean isVerified) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("isVerified"), isVerified);
    }
}
