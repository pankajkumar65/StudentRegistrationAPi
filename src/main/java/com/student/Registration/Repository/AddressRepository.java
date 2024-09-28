package com.student.Registration.Repository;

import com.student.Registration.Entity.addressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AddressRepository extends JpaRepository<addressEntity, Integer>, JpaSpecificationExecutor<addressEntity> {
}
