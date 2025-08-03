package com.example.Employee_Directory.controller;

import com.example.Employee_Directory.dto.EmployeeDTO;
import com.example.Employee_Directory.model.Employee;
import com.example.Employee_Directory.repository.employee.EmployeeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest {

    @LocalServerPort
    private int port;


    private TestRestTemplate restTemplate;

    @Autowired
    private EmployeeRepo employeeRepo;


    private String baseUrl;


    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/employees";
        restTemplate = new TestRestTemplate("admin", "admin");
        //employeeRepo.deleteAll();
    }

    @Test
    void testAddEmployee() {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setEmail("john.doe@example.com");

        ResponseEntity<Employee> response = restTemplate.postForEntity(baseUrl, dto, Employee.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getFirstName()).isEqualTo("John");
    }
}
