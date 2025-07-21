package com.example.Employee_Directory.exception.global;

import com.example.Employee_Directory.exception.custom.EmployeeNotFoundException;
import com.example.Employee_Directory.exception.errorResponse.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    ResponseEntity<ErrorResponse> handleEmployeeNotFound(EmployeeNotFoundException exp, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse(
                exp.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                "User Not Found",
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    ResponseEntity<ErrorResponse> handleGlobalException(Exception exception, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse(
                "An unexpected error occurred: " + exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
