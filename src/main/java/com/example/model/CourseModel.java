package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CourseModel {
	private String idCourse;
	private String name;
	private Integer credits;
	private List<StudentModel> students;
}
