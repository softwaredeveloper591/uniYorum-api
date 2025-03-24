package com.enes.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;



@Entity
@Table(name = "bolumler")
@Getter
@Setter

public class Department {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Integer departmentId;

	@OneToOne
	@JoinColumn(name = "uni_id", referencedColumnName = "uni_id")
	@NotNull(message = "Uni Id can't be null")
    private University university;

    @Column(name = "bolum_ad", length = 255, nullable = false)
    private String bolumAd;

    @ManyToOne
    @JoinColumn(name = "fakulte_id", nullable = false)
    private Faculty faculty;

    @Column(name = "status", nullable = false)
    private Integer status;

}
