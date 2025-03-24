package com.enes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enes.model.entities.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

}
