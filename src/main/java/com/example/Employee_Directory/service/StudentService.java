package com.example.Employee_Directory.service;

import com.example.Employee_Directory.model.Student;

import java.util.List;

public interface StudentService {


   List<Student> getAllStudents();

   Student addStudent(Student student);
}
