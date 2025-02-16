package com.tcs.Practice.controller;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tcs.Practice.entity.Employee;
import com.tcs.Practice.services.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/")
	public String BaseUrl() {
		return "<h1>Hello Siddhant</h1>";
	}
	
	@PostMapping("/addEmployee")
	public Employee addEmployee(@RequestBody Employee employee) {
		
		// Multiple Employee can have One Address
		Employee emp = employeeService.addEmployeeDetails(employee);
		return emp;
	}
	
	@GetMapping("/ViewAllEmployee")
	public ResponseEntity<String> viewAllEmployee() {
	    List<Employee> employees = employeeService.viewAllEmployee();

	    if (employees.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No employees found!");
	    } else {
	        String result = employees.stream()
	                .map(emp -> "ID: " + emp.getEmpId() + ", Name: " + emp.getEmpName() + 
	                            ", Mobile: " + emp.getEmpMobile() + ", Salary: " + emp.getEmpSalary() + ", Street: " +
	                		emp.getAddress().getStreet() + ", City: " + emp.getAddress().getCity()+ ", State: " +
	                		emp.getAddress().getState() + ", Pin: " + emp.getAddress().getZipCode())
	                .collect(Collectors.joining("\n"));

	        return ResponseEntity.ok(result);
	    }
	}
	
	@GetMapping("/ViewEmployee/{id}")
	public ResponseEntity<String> viewEmployee(@PathVariable Long id) {
		Employee emp = employeeService.viewEmployee(id);
		
		if (emp == null ) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee ID : " + id +  " is not found!");
	    } else {
	        String result = "ID: " + emp.getEmpId() + ", Name: " + emp.getEmpName() + 
	                            ", Mobile: " + emp.getEmpMobile() + ", Salary: " + emp.getEmpSalary() + ", Street: " +
	                		emp.getAddress().getStreet() + ", City: " + emp.getAddress().getCity()+ ", State: " +
	                		emp.getAddress().getState() + ", Pin: " + emp.getAddress().getZipCode();

	        return ResponseEntity.ok(result);
	    }
	}
	
	@PutMapping("/UpdateEmployee/{id}")
	public ResponseEntity<String> viewEmployee(@RequestBody Employee employee , @PathVariable Long id) {
		Employee emp = employeeService.updateEmployee(employee,id);
		
		if (emp == null ) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee ID : " + id +  " is not found!");
	    } else {
	        String result = "Updated Successfully:\nID: " + emp.getEmpId() + ", Name: " + emp.getEmpName() + 
	                            ", Mobile: " + emp.getEmpMobile() + ", Salary: " + emp.getEmpSalary() + ", Street: " +
	                		emp.getAddress().getStreet() + ", City: " + emp.getAddress().getCity()+ ", State: " +
	                		emp.getAddress().getState() + ", Pin: " + emp.getAddress().getZipCode();

	        return ResponseEntity.ok(result);
	    }
	}
	
	@DeleteMapping("/DeleteEmployee/{id}")
	public ResponseEntity<String> DeleteEmployee(@PathVariable Long id) {
		String res = employeeService.deleteEmployee(id);
		
		if (res == null ) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee ID : " + id +  " is not found!");
	    } else {
	        return ResponseEntity.ok(res);
	    }
	}
	
	@DeleteMapping("/DeleteAllEmployee")
	public String deleteAllEmployee() {
	    employeeService.deleteAllEmployee();
	    
	    return "All record are deleted";
	}

}