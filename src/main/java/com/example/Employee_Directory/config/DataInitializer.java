package com.example.Employee_Directory.config;

import com.example.Employee_Directory.model.Employee;
import com.example.Employee_Directory.model.Role;
import com.example.Employee_Directory.model.User;
import com.example.Employee_Directory.repository.EmployeeRepo;
import com.example.Employee_Directory.repository.RoleRepository;
import com.example.Employee_Directory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

@Component
@Profile("test")
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmployeeRepo employeeRepo;

    @Override
    public void run(String... args) throws Exception {
        Role empRole = new Role("EMPLOYEE");
        Role manRole = new Role("MANAGER");
        Role adminRole = new Role("ADMIN");

        roleRepository.saveAll(Set.of(empRole, manRole, adminRole));

        User user = new User();
        user.setUsername("employee");
        user.setPassword("$2a$12$9ybhAnvPkQoyMSnp73o1XeWIi3zTtM0.45WigdEXt6buq.cAjW5VC");
        user.setRoles(Set.of(empRole));  // Assign EMPLOYEE role

        User user2 = new User();
        user2.setUsername("admin");
        user2.setPassword("$2a$12$HzxQVbeAyNyqsoG0ggtYhOKRV/CXwgPvBebKZTeygNJBLLRO3yHxO");
        user2.setRoles(Set.of(adminRole));  // Assign ADMIN role

        userRepository.save(user);
        userRepository.save(user2);

        Employee employee = new Employee("Aamir","Usman","aamir.usman@gmail.com");
        employee.setCreatedAt(new Date());


        employeeRepo.save(employee);
    }
}
