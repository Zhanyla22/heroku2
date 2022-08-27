package com.example.SpringZhanyl.Repository;

import com.example.SpringZhanyl.Entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {
}
