package com.jpadata.sb.controller;
import com.jpadata.sb.dto.EmployeeDto;
import com.jpadata.sb.exception.ResponseNotFoundException;
import com.jpadata.sb.model.Employee;

import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;


public interface EmployeeController {

	public List<EmployeeDto> getAllEmployees();

	public ResponseEntity<EmployeeDto> getEmployeeById(Long empId) throws ResponseNotFoundException;
	public EmployeeDto createEmployee(Employee employee);
	public ResponseEntity<EmployeeDto> updateEmployee(Long empId, Employee employee) throws ResponseNotFoundException;
	public Map<String, Boolean> deleteEmployee(Long empId) throws ResponseNotFoundException;
}
