package com.example.Employee_Directory.service.service_impl;

import com.example.Employee_Directory.model.Employee;
import com.example.Employee_Directory.repository.EmployeeRepo;
import com.example.Employee_Directory.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> allEmployees = employeeRepo.findAll();
        return allEmployees;
    }

    @Override
    public Employee addEmployee(Employee emp) {
        return employeeRepo.save(emp);
    }

    @Override
    public Optional<Employee> getEmployeeById(Integer id) {
        return employeeRepo.findById(id);
    }

    @Override
    public Employee updateEmployee(Integer id, Employee employee) {
        try {
            Employee emp = employeeRepo.getReferenceById(id);
            emp.setFirstName(employee.getFirstName());
            emp.setLastName(employee.getLastName());
            emp.setEmail(employee.getEmail());
            return employeeRepo.save(emp);
        } catch (EntityNotFoundException exception) {
            throw new RuntimeException("Employee not found with id: " + id);
        }
    }

    @Override
    public String deleteEmployee(Integer id) {

            if(!employeeRepo.existsById(id)){
                throw new RuntimeException("Employee not found with id: "+id);
            }

            employeeRepo.deleteById(id);
            return "Employee with id: "+id +" deleted successfully";


    }
}
