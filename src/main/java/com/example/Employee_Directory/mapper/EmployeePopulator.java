package com.example.Employee_Directory.mapper;

import com.example.Employee_Directory.dto.EmployeeDTO;
import com.example.Employee_Directory.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeePopulator {
    EmployeePopulator INSTANCE = Mappers.getMapper(EmployeePopulator.class);
    Employee populateEmployee(EmployeeDTO employeeDTO);
}
