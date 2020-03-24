package com.jpadata.sb.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
//import java.util.Optional;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Entity;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.jpadata.sb.dto.EmployeeDto;
import com.jpadata.sb.exception.ResponseNotFoundException;
import com.jpadata.sb.model.Employee;
import com.jpadata.sb.repository.EmployeeRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@RestController 
@RequestMapping(value="/api/v1")
public class EmployeeControllerImpl implements EmployeeController {
	
	Logger log= LoggerFactory.getLogger(EmployeeControllerImpl.class);

	@Autowired
	public EmployeeRepository employeeRepository;
	
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	DozerBeanMapper dozerMapper;
	
	@Value("${server.port}")
	String serverPort;

	
	@Override
	@GetMapping("/employees")
	@HystrixCommand(fallbackMethod="fallback_empl",commandProperties= {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
	})
	public List<EmployeeDto> getAllEmployees() {
		List<Employee> resultDB = employeeRepository.findAll();
		List<EmployeeDto> res= resultDB.stream()
				.map(data->dozerMapper.map(data,EmployeeDto.class))
				.collect(Collectors.toList());
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

		Employee resultdb = employeeRepository.save(employee);
		System.out.println("Result from db "+resultdb);
		EmployeeDto result = dozerMapper.map(resultdb,EmployeeDto.class);
		System.out.println(result);
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


	@RequestMapping(value="/template/emp")
	public String getEmployeeList()
	{
		HttpHeaders header= new HttpHeaders();
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity=new HttpEntity<String>(header);
		//return restTemplate.exchange("http://localhost:8082/api/v1/employees/1",HttpMethod.GET,entity,String.class).getBody();
		return restTemplate.exchange("http://localhost:"+serverPort+"/api/v1/employees/1",HttpMethod.GET,entity,String.class).getBody();
	}
	
	@RequestMapping(value="/template/updateEmp/{id}",method=RequestMethod.PUT)
	public String updateEmployeesTemplate(@RequestBody EmployeeDto emp,@PathVariable("id") String id)
	{
		HttpHeaders header= new HttpHeaders();
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	HttpEntity<EmployeeDto> entity= new HttpEntity<EmployeeDto>(emp,header);
	return restTemplate.exchange("http://localhost:"+serverPort+"/api/v1/employees/"+id,HttpMethod.PUT,entity,String.class).getBody();
	
	}
	
	private List<EmployeeDto> fallback_empl() {
		   return new ArrayList<EmployeeDto>();
		}
}
