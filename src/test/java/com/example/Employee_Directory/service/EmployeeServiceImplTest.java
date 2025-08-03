package com.example.Employee_Directory.service;

import com.example.Employee_Directory.dto.EmployeeDTO;
import com.example.Employee_Directory.mapper.EmployeePopulator;
import com.example.Employee_Directory.model.Employee;
import com.example.Employee_Directory.repository.employee.EmployeeRepo;
import com.example.Employee_Directory.service.service_impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EmployeeServiceImplTest {

    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    EmployeeServiceImpl employeeServiceImpl;

    @Test
    public void testAdd(){
        assertEquals(4,2+2);
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "3,3,6"
    })
    public void test(int a, int b, int expected){
        assertEquals(expected, a + b);
    }

    @Test
    void testGetAllEmployees(){
        List<Employee> allEmployees = employeeRepo.findAll();
                assertFalse(allEmployees.isEmpty());
    }

    @Test
    void testAddEmployeeTest(){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("test1FirstName");
        employeeDTO.setLastName("test1LastName");
        employeeDTO.setEmail("test1Email");
        Employee addedEmployee = employeeServiceImpl.addEmployee(employeeDTO);
        assertNotNull(addedEmployee.getId());
        assertEquals(employeeDTO.getFirstName(),addedEmployee.getFirstName());
    }

    @Test
    void testGetEmployeeById(){
        Employee firstEmployee = employeeServiceImpl.getAllEmployees().getFirst();
        assertNotNull(firstEmployee.getId());
        Optional<Employee> employee = employeeServiceImpl.getEmployeeById(firstEmployee.getId());
        assertTrue(employee.isPresent());
        assertEquals(firstEmployee.getFirstName(),employee.get().getFirstName());
    }

    @Test
    void testUpdateEmployee(){
        Employee firstEmployee = employeeServiceImpl.getAllEmployees().getFirst();

        assertNotNull(firstEmployee.getId());
        firstEmployee.setFirstName("updated");
        // Use mapper to convert Employee -> EmployeeDTO
        EmployeeDTO employeeDTO = EmployeePopulator.INSTANCE.populateEmployeeDTO(firstEmployee);
        Employee updatedEmployee = employeeServiceImpl.updateEmployee(firstEmployee.getId(), employeeDTO);
        assertEquals("updated",updatedEmployee.getFirstName());
    }

    @Test
    void tetDeleteEmployee(){
        Employee firstEmployee = employeeServiceImpl.getAllEmployees().getFirst();
        Integer id = firstEmployee.getId();
        assertNotNull(id);
        String result = employeeServiceImpl.deleteEmployee(firstEmployee.getId());
        assertTrue(result.contains("Employee with id: "+id+" deleted successfully"));
    }
}
