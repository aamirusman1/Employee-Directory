package com.example.Employee_Directory.service.service_impl;

import com.example.Employee_Directory.dto.EmployeeDTO;
import com.example.Employee_Directory.mapper.EmployeePopulator;
import com.example.Employee_Directory.model.Employee;
import com.example.Employee_Directory.repository.employee.EmployeeRepo;
import com.example.Employee_Directory.service.EmployeeService;
import com.example.Employee_Directory.service.service_impl.redis.RedisCacheService;
import com.example.Employee_Directory.utils.RedisCacheKeyGenerator;
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

    private RedisCacheKeyGenerator redisCacheKeyGenerator;

    private RedisCacheService redisCacheService;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepo employeeRepo, RedisCacheKeyGenerator redisCacheKeyGenerator, RedisCacheService redisCacheService) {
        this.employeeRepo = employeeRepo;
        this.redisCacheKeyGenerator = redisCacheKeyGenerator;
        this.redisCacheService = redisCacheService;
    }

    @Override
    public List<Employee> getAllEmployees() {
        String cacheKey = redisCacheKeyGenerator.getAllEmployeesKey();

        // Getting from cache first
        Optional<List<Employee>> cachedEmployees = redisCacheService.getValueList(cacheKey, Employee.class);

        if (cachedEmployees.isPresent()) {
            log.info("Retrieved {} employees from cache", cachedEmployees.get().size());
            return cachedEmployees.get();
        }
        // Not found in cache, get from db
        List<Employee> allEmployees = employeeRepo.findAll();
        log.info("Retrieved {} employees from database", allEmployees.size());

        // Cache the result for 30 minutes (1800 seconds)
        redisCacheService.setValueWithExpiry(cacheKey, allEmployees, 1800);
        return allEmployees;
    }

    @Override
    public Employee addEmployee(EmployeeDTO empDTO) {
        Employee employee = EmployeePopulator.INSTANCE.populateEmployee(empDTO);
        employee.setCreatedAt(new Date());
        Employee savedEmployee = employeeRepo.save(employee);
        log.info("Employee added in db : {}", employee);
        //Save this added employee in cache also
        String cacheKey = redisCacheKeyGenerator.getEmployeeByIdKey(savedEmployee.getId());
        redisCacheService.setValue(cacheKey,employee);
        log.info("Employee also added in cache : {}", employee);
        //Delete allEmployee cache
        String cacheKeyAllEmployees = redisCacheKeyGenerator.getAllEmployeesKey();
        redisCacheService.deleteKey(cacheKeyAllEmployees);
        log.info("current cache all employees list deleted");

        return savedEmployee;
    }

    @Override
    public Optional<Employee> getEmployeeById(Integer id) {
        //First check in cache
        String cacheKey = redisCacheKeyGenerator.getEmployeeByIdKey(id);
        Optional<Employee> cachedEmployee = redisCacheService.getValue(cacheKey, Employee.class);
        if (cachedEmployee.isPresent()) {
            log.info("Retrieved {} employee from cache", cachedEmployee.get());
            return cachedEmployee;
        }
        //If not in cache find from db
        Optional<Employee> employee = employeeRepo.findById(id);
        log.debug("Fetched employee from database ");
        return employee;
    }

    @Override
    @Transactional
    public Employee updateEmployee(Integer id, EmployeeDTO employeeDTO) {
        try {
            Employee emp = employeeRepo.getReferenceById(id);
            EmployeePopulator.INSTANCE.updateEmployeeFromDTO(employeeDTO, emp);
            emp.setCreatedAt(new Date());
            Employee updatedEmploye = employeeRepo.save(emp);
            log.info("Employee updated from database : {}", emp);
            //Update cache of the updated employee
            String cacheKey = redisCacheKeyGenerator.getEmployeeByIdKey(id);
            redisCacheService.setValue(cacheKey, updatedEmploye);
            //Delete allEmployee cache
            deleteAllEmployeeListCache();
            return updatedEmploye;

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
            log.info("Employee deleted from database : {}", id);
            //Delete cache of this employee
        String cacheKey = redisCacheKeyGenerator.getEmployeeByIdKey(id);
        redisCacheService.deleteKey(cacheKey);
        log.debug("deleted employee cache removed");
        //Delete allEmployee list cache
        deleteAllEmployeeListCache();

            return "Employee with id: "+id +" deleted successfully";

    }

    public void deleteAllEmployeeListCache(){
        String cacheKeyAllEmployees = redisCacheKeyGenerator.getAllEmployeesKey();
        redisCacheService.deleteKey(cacheKeyAllEmployees);
        log.info("current cache of all employees list deleted");
    }
}
