package com.example.Employee_Directory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeeDirectoryApplication {

	private static final Logger logger =LoggerFactory.getLogger(EmployeeDirectoryApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(EmployeeDirectoryApplication.class, args);
		System.out.println("Application Emp Directory started");
		logger.info("Application Emp Directory started using log");
	}

}
