package com.enes.model.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fakulteler") // Mapping to the database table
@Getter
@Setter
public class Faculty {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fakulte_id")
    private Integer fakulteId;

    @Column(name = "fakulte_ad", length = 255, nullable = false)
    private String fakulteAd;

    @Column(name = "uni_id", nullable = false)
    private Integer uniId;

    @Column(name = "status", nullable = false)
    private Integer status;

    @OneToMany(mappedBy = "faculty")
    private List<Department> departments;
}
