package com.enes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enes.entities.Admin;

public interface AdminRepo extends JpaRepository<Admin, Long> {

}
