package com.enes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enes.model.entities.University;

public interface UniversityRepository extends JpaRepository<University, Integer> {

}
