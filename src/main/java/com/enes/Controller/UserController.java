package com.enes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.enes.repository.AdminRepo;


@RestController
public class UserController {
	
	@Autowired
	private AdminRepo adminRepo;
	
	@GetMapping(path = "admin/{id}")
    public void login(@PathVariable Long id) {
		System.out.println(adminRepo.findById(id));
	}

}
