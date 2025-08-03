package com.example.Employee_Directory.service;

import com.example.Employee_Directory.dto.EmployeeDTO;
import com.example.Employee_Directory.mapper.EmployeePopulator;
import com.example.Employee_Directory.model.Employee;
import com.example.Employee_Directory.repository.employee.EmployeeRepo;
import com.example.Employee_Directory.service.service_impl.EmployeeServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceImplUsingMockitoTest {

    @Mock
    EmployeeRepo employeeRepo;

    @InjectMocks
    EmployeeServiceImpl employeeServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllEmployees() {
        List<Employee> mockEmployees = Arrays.asList(new Employee(), new Employee());
        when(employeeRepo.findAll()).thenReturn(mockEmployees);

        List<Employee> allEmployees = employeeServiceImpl.getAllEmployees();

        assertEquals(2, allEmployees.size());
        verify(employeeRepo, times(1)).findAll();
        //To verify findAll() method of the employeeRepo mock was called exactly once during the test execution
    }

    @Test
    void testAddEmployee() {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setEmail("john@example.com");

        Employee mappedEmployee = EmployeePopulator.INSTANCE.populateEmployee(dto);
        mappedEmployee.setId(1);
        mappedEmployee.setCreatedAt(new Date());

        when(employeeRepo.save(any(Employee.class))).thenReturn(mappedEmployee);
        //any(Employee.class) – No need to pass any specific Employee instance; it can be any instance of Employee.

        Employee result = employeeServiceImpl.addEmployee(dto);

        assertNotNull(result.getId());
        assertEquals(dto.getFirstName(), result.getFirstName());
        verify(employeeRepo).save(any(Employee.class));
        // same as: verify(employeeRepo, times(1)).save(any(Employee.class));
        // to confirm the save(...) method of the employeeRepo mock was called once with any instance of Employee.
    }

    @Test
    void testGetEmployeeById() {
        Employee emp = new Employee();
        emp.setId(1);
        emp.setFirstName("Jane");

        when(employeeRepo.findById(1)).thenReturn(Optional.of(emp));

        Optional<Employee> result = employeeServiceImpl.getEmployeeById(1);

        assertTrue(result.isPresent());
        assertEquals("Jane", result.get().getFirstName());
        verify(employeeRepo).findById(1);
    }

    @Test
    void testUpdateEmployee() {

        Employee existing = new Employee();
        existing.setId(1);
        existing.setFirstName("Old");
        existing.setLastName("Original");
        existing.setEmail("old@example.com");


        when(employeeRepo.getReferenceById(1)).thenReturn(existing);
        //To return the same Employee object passed in, when employeeRepo.save(...) is called with any Employee
        when(employeeRepo.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Create the DTO with updated values
        EmployeeDTO updatedDto = new EmployeeDTO();
        updatedDto.setFirstName("New");
        updatedDto.setLastName("Updated");
        updatedDto.setEmail("updated@example.com");

        Employee result = employeeServiceImpl.updateEmployee(1, updatedDto);

        assertEquals("New", result.getFirstName());
        assertEquals("Updated", result.getLastName());
        assertEquals("updated@example.com", result.getEmail());

        verify(employeeRepo).getReferenceById(1);
        verify(employeeRepo).save(existing);
    }

    @Test
    void testUpdateEmployee_ThrowsIfNotFound() {
        when(employeeRepo.getReferenceById(99)).thenThrow(new EntityNotFoundException());
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            employeeServiceImpl.updateEmployee(99, new EmployeeDTO());
        });
        assertEquals("Employee not found with id: 99", thrown.getMessage());
    }

    @Test
    void testDeleteEmployee_Success() {
        Integer employeeId = 1;
        when(employeeRepo.existsById(employeeId)).thenReturn(true);

        // Here no need to define when(employeeRepo.deleteEmployee() because this method returns void
        String result = employeeServiceImpl.deleteEmployee(employeeId);

        assertEquals("Employee with id: 1 deleted successfully", result);
        verify(employeeRepo).existsById(employeeId);
        verify(employeeRepo).deleteById(employeeId);
    }

    @Test
    void testDeleteEmployee_ThrowsExceptionWhenNotFound() {
        Integer employeeId = 2;
        when(employeeRepo.existsById(employeeId)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> employeeServiceImpl.deleteEmployee(employeeId));

        assertEquals("Employee not found with id: 2", exception.getMessage());
        verify(employeeRepo).existsById(employeeId);
        verify(employeeRepo, never()).deleteById(any());
    }





}
