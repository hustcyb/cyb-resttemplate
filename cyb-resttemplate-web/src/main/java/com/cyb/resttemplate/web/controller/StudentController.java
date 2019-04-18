package com.cyb.resttemplate.web.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.ws.rs.BadRequestException;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyb.resttemplate.web.domain.Student;

@RequestMapping("students")
@RestController
public class StudentController {

	private static final Map<Integer, Student> students = new HashMap<Integer, Student>();

	@GetMapping
	public Collection<Student> listStudents(String name) {
		if (StringUtils.isEmpty(name)) {
			return students.values();
		}

		List<Student> studentList = new ArrayList<Student>();
		for (Student student : students.values()) {
			if (Objects.equals(student.getName(), name)) {
				studentList.add(student);
			}
		}

		return studentList;
	}

	@GetMapping("{id}")
	public Student getStudentById(@PathVariable Integer id) {
		return students.get(id);
	}

	@PostMapping
	public void saveStudent(@RequestBody Student student) {
		if (student == null) {
			throw new BadRequestException("请填写学生信息");
		}

		if (student.getId() == null) {
			throw new BadRequestException("请填写学生编号");
		}

		if (StringUtils.isEmpty(student.getName())) {
			throw new BadRequestException("请填写学生姓名");
		}

		students.put(student.getId(), student);
	}

	@DeleteMapping("{id}")
	public void deleteStudent(@PathVariable Integer id) {
		students.remove(id);
	}

}
