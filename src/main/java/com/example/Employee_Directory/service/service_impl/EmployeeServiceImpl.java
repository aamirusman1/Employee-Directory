package com.example.Employee_Directory.service.service_impl;

import com.example.Employee_Directory.dto.EmployeeDTO;
import com.example.Employee_Directory.mapper.EmployeePopulator;
import com.example.Employee_Directory.model.Employee;
import com.example.Employee_Directory.repository.employee.EmployeeRepo;
import com.example.Employee_Directory.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
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
    public Employee addEmployee(EmployeeDTO empDTO) {
        Employee employee = EmployeePopulator.INSTANCE.populateEmployee(empDTO);
        employee.setCreatedAt(new Date());
        log.info("Employee added (service layer log) : {}", employee);
        return employeeRepo.save(employee);
    }

    @Override
    public Optional<Employee> getEmployeeById(Integer id) {
        return employeeRepo.findById(id);
    }

    @Override
    @Transactional
    public Employee updateEmployee(Integer id, EmployeeDTO employeeDTO) {
        try {
            Employee emp = employeeRepo.getReferenceById(id);

            // Populate DTO values into the existing entity
            EmployeePopulator.INSTANCE.updateEmployeeFromDTO(employeeDTO, emp);

            // Optionally set updatedAt
            emp.setCreatedAt(new Date());  // if you have such a field

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
