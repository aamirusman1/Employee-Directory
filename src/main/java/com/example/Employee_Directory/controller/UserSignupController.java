package com.example.Employee_Directory.controller;

import com.example.Employee_Directory.dto.SignupRequest;
import com.example.Employee_Directory.model.Role;
import com.example.Employee_Directory.model.User;
import com.example.Employee_Directory.repository.role.RoleRepository;
import com.example.Employee_Directory.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@Slf4j
@RestController
@RequestMapping("/api/signup")
public class UserSignupController {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;


    // Common signup handler
    private ResponseEntity<String> registerUser(SignupRequest request, String roleName) {
        if (userRepo.findByUsername(request.getUsername()).isPresent()) {
            return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
        }

        Role role = roleRepo.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));

        User newUser = new User();

        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRoles(Collections.singleton(role));

        userRepo.save(newUser);

        return new ResponseEntity<>("User registered with role " + roleName, HttpStatus.CREATED);
    }

    @PostMapping("/employee")
    public ResponseEntity<String> registerEmployee(@RequestBody SignupRequest request) {
        log.info("Received request to signup for user {}", request.getUsername());
        return registerUser(request, "EMPLOYEE");
    }

    @PostMapping("/manager")
    public ResponseEntity<String> registerManager(@RequestBody SignupRequest request) {
        return registerUser(request, "MANAGER");
    }

    @PostMapping("/admin")
    public ResponseEntity<String> registerAdmin(@RequestBody SignupRequest request) {
        return registerUser(request, "ADMIN");
    }
}
