package com.example.Employee_Directory.exception.custom;

public class EmployeeNotFoundException extends RuntimeException{
    public EmployeeNotFoundException(){
        super("Employee Not Found");
    }
    public EmployeeNotFoundException(String message){
        super(message);
    }
    // Constructor with message and cause
    public EmployeeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    // Constructor with cause only
    public EmployeeNotFoundException(Throwable cause) {
        super("Employee not found", cause);
    }
}
