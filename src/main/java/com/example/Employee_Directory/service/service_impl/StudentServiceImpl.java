package com.example.Employee_Directory.service.service_impl;

import com.example.Employee_Directory.model.Student;
import com.example.Employee_Directory.repository.student.StudentRepository;
import com.example.Employee_Directory.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepo;

    @Override
    public List<Student> getAllStudents() {
        List<Student> allStudents = studentRepo.findAll();
        return allStudents;
    }

    @Override
    public Student addStudent(Student student) {
        Student savedStudent = studentRepo.save(student);
        return savedStudent;
    }
}
