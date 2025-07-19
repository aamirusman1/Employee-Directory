package com.example.Employee_Directory.service;

import com.example.Employee_Directory.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    Employee addEmployee(Employee emp);
    Optional<Employee> getEmployeeById(Integer id);
    Employee updateEmployee(Integer id, Employee employee);
    String deleteEmployee(Integer id);
}
