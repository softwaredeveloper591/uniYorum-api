package com.enes.model.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "universiteler")
public class University {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uni_id", nullable = false)
    private Integer uniId;

    @Column(name = "uni_name", nullable = false, length = 255)
    private String uniName;

    @Column(name = "il_id", nullable = false)
    private Integer ilId;

    @Column(name = "status", nullable = false)
    private Integer status;
}
