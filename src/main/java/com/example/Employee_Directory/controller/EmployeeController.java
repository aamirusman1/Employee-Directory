package com.example.Employee_Directory.controller;

import com.example.Employee_Directory.exception.custom.EmployeeNotFoundException;
import com.example.Employee_Directory.model.Employee;
import com.example.Employee_Directory.service.service_impl.EmployeeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Slf4j
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    //private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private EmployeeServiceImpl employeeService;

    @Autowired
    public EmployeeController(EmployeeServiceImpl employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees(){
        List<Employee> allEmployees = employeeService.getAllEmployees();
        if(allEmployees.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //204 No Content
        }
        log.info("getAllEmployees returned {}", allEmployees);
        return new ResponseEntity<>(allEmployees, HttpStatus.OK); //200 OK
    }

    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee emp){
        Employee savedEmployee = employeeService.addEmployee(emp);
        log.info("addEmployee returned {}", savedEmployee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED); //201 Created
    }

    @GetMapping("/{id}")
    public  ResponseEntity<Employee> getEmployeeById(@PathVariable Integer id){
        Optional<Employee> emp = employeeService.getEmployeeById(id);
        if(emp.isPresent()){
            log.info("getEmployeeById returned {}", emp);
            return new ResponseEntity<>(emp.get(), HttpStatus.OK);
        }
        //return new ResponseEntity<>(HttpStatus.NOT_FOUND); //404 Not Found
        log.warn("Employee with id {} not found", id);
        throw new EmployeeNotFoundException("Employee with id: "+ id +" does not exist"); //here we get custom response
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEmployee(@PathVariable Integer id, @RequestBody Employee emp ) {
        try {
            Employee updatedEmployee = employeeService.updateEmployee(id, emp);
            log.info("updateEmployee returned {}", updatedEmployee);
            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
        } catch (RuntimeException e) {
            //return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            log.warn("Employee with id {} not found", id);
            throw new EmployeeNotFoundException("Employee with id: "+ id +" does not exist"); //here we get custom response
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Integer id){
        try {
            String employeeDeleted = employeeService.deleteEmployee(id);
            log.info("deleteEmployee returned {}", employeeDeleted);
            return new ResponseEntity<>(employeeDeleted, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.warn("Employee with id {} not found", id);
           //return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            throw new EmployeeNotFoundException("Employee with id: "+ id +" does not exist"); //here we get custom response
        }
    }



}
