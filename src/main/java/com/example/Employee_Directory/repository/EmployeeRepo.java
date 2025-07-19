package com.example.Employee_Directory.repository;

import com.example.Employee_Directory.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {


}
