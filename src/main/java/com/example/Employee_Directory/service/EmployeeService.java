package com.example.Employee_Directory.service;

import com.example.Employee_Directory.dto.EmployeeDTO;
import com.example.Employee_Directory.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    Employee addEmployee(EmployeeDTO empDTO);
    Optional<Employee> getEmployeeById(Integer id);
    Employee updateEmployee(Integer id, EmployeeDTO employeeDTO);
    String deleteEmployee(Integer id);
}
