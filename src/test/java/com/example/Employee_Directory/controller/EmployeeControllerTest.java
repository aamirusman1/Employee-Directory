package com.example.Employee_Directory.controller;

import com.example.Employee_Directory.config.TestSecurityConfig;
import com.example.Employee_Directory.dto.EmployeeDTO;
import com.example.Employee_Directory.model.Employee;
import com.example.Employee_Directory.model.Role;
import com.example.Employee_Directory.model.User;
import com.example.Employee_Directory.repository.employee.EmployeeRepo;
import com.example.Employee_Directory.repository.role.RoleRepository;
import com.example.Employee_Directory.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

//@Import(TestSecurityConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class EmployeeControllerTest {

    @LocalServerPort
    private int port;


    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    private String baseUrl;


    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/employees";
        //restTemplate = new TestRestTemplate("admin", "admin");
        // Wrap TestRestTemplate with credentials

//        Role adminRole = new Role("ROLE_ADMIN");
//        roleRepository.save(adminRole);
//
//        User user = new User();
//        user.setUsername("admin");
//        user.setPassword(passwordEncoder.encode("admin"));
//        user.setRoles(Set.of(adminRole));
//        userRepository.save(user);

    }



    @Test
    void testAddEmployee() {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setFirstName("Aamir");
        dto.setLastName("Usman");
        dto.setEmail("john.doe@example.com");

        TestRestTemplate authRestTemplate = restTemplate.withBasicAuth("admin", "admin");

        ResponseEntity<Employee> response = authRestTemplate
                .postForEntity(baseUrl, dto, Employee.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getFirstName()).isEqualTo("Aamir");
    }
}
