package com.example.Employee_Directory.mapper;

import com.example.Employee_Directory.dto.EmployeeDTO;
import com.example.Employee_Directory.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeePopulator {
    EmployeePopulator INSTANCE = Mappers.getMapper(EmployeePopulator.class);

    // Creates a new Employee object from DTO
    Employee populateEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO populateEmployeeDTO(Employee employee);

    // Updates an existing Employee entity with data from DTO
    void updateEmployeeFromDTO(EmployeeDTO employeeDTO,@MappingTarget Employee employee);
}
