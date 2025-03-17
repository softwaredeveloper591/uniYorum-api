package com.enes.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "student_file")
public class StudentFile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	@Column(name = "fileName")
	private String fileName;

	@Lob
	@NotNull
	@Column(name = "fileData", columnDefinition = "LONGBLOB")
	private byte[] fileData;
	
	@NotNull
	@Column(name = "mimeType")
	private String mimeType;
	
	@NotNull
	@OneToOne
	@JoinColumn(name = "studentId")
	private Student student;

}
