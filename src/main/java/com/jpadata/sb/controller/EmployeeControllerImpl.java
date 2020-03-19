package com.jpadata.sb.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
//import java.util.Optional;
import java.util.Map;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jpadata.sb.dto.EmployeeDto;
import com.jpadata.sb.exception.ResponseNotFoundException;
import com.jpadata.sb.model.Employee;
import com.jpadata.sb.repository.EmployeeRepository;

@RestController 
@RequestMapping(value="/api/v1")
public class EmployeeControllerImpl implements EmployeeController {

	@Autowired
	public EmployeeRepository employeeRepository;

	@Autowired
	DozerBeanMapper dozerMapper;

	@Override
	@GetMapping("/employees")
	public List<EmployeeDto> getAllEmployees() {
		List<Employee> resultDB = employeeRepository.findAll();
		EmployeeDto finalResult = dozerMapper.map(resultDB,EmployeeDto.class);
		List<EmployeeDto> res= new ArrayList<>(Arrays.asList(finalResult));
		return res; 
	}

	@Override
	@GetMapping("/employees/{id}")
	public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable(value= "id") Long empId) throws ResponseNotFoundException {
		/*Optional<Employee> resultdb = employeeRepository.findById(empId);
		if(resultdb==null)
		{
			throw new ResourseNotFoundException("resouse not found for this id "+ empId);
		}*/
		Employee resultdb = employeeRepository.findById(empId)
				.orElseThrow(()->new ResponseNotFoundException("No record Found for id "+ empId));
		EmployeeDto res = dozerMapper.map(resultdb,EmployeeDto.class);
		return ResponseEntity.ok().body(res);
	
		
	}

	@Override
	@PostMapping("/employees")
	public EmployeeDto createEmployee(@RequestBody Employee employee) {

		Employee resultdb = this.employeeRepository.save(employee);
		EmployeeDto result = dozerMapper.map(resultdb,EmployeeDto.class);
		return result;
	}

	@Override
	@PutMapping("/employees/{id}")
	public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable(value="id" ) Long empId , @Validated @RequestBody Employee employeeDetails)throws ResponseNotFoundException {
		Employee resultdb = employeeRepository.findById(empId)
				.orElseThrow(()->new ResponseNotFoundException("No record Found for id "+ empId));


		resultdb.setfName(employeeDetails.getfName());
		resultdb.setlName(employeeDetails.getlName());
		resultdb.setEmail(employeeDetails.getEmail());
		Employee updatedResponse = employeeRepository.save(resultdb);
		EmployeeDto res = dozerMapper.map(updatedResponse, EmployeeDto.class);
		return ResponseEntity.ok().body(res);

	}
	@DeleteMapping("/employees/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value="id") Long empId) throws ResponseNotFoundException{
		//employeeRepository.deleteById(empId);
		Employee resultdb = employeeRepository.findById(empId)
				.orElseThrow(()->new ResponseNotFoundException("No record Found for id "+ empId));
		this.employeeRepository.delete(resultdb);
		Map<String, Boolean> response = new HashMap<String , Boolean>();
		response.put(empId+" deleted ", true);
		return response;

	}
	@GetMapping(value="/hello")
    public String sayHello() {
        return "Hello World!";
    }   


}
