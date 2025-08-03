package com.example.Employee_Directory.repository.employee;

import com.example.Employee_Directory.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {


}
