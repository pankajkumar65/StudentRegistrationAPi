package com.student.Registration.Specification;

import com.student.Registration.Entity.addressEntity;
import org.springframework.data.jpa.domain.Specification;

public class AddressSpecification {

    public static Specification<addressEntity> hasStreet(String street) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("street"), street);
    }

    public static Specification<addressEntity> hasCity(String city) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("city"), city);
    }

    public static Specification<addressEntity> hasState(String state) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("state"), state);
    }

    public static Specification<addressEntity> hasCountry(String country) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("country"), country);
    }

    public static Specification<addressEntity> hasZipCode(String zipCode) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("zipCode"), zipCode);
    }
}
