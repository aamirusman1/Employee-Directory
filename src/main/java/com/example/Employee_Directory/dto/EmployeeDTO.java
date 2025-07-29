package com.example.Employee_Directory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    private String firstName;
    private String lastName;
    private String email;
}
