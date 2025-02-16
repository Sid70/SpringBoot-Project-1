package com.tcs.Practice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tcs.Practice.entity.Address;
import com.tcs.Practice.entity.Employee;
import com.tcs.Practice.repository.AddressRepository;
import com.tcs.Practice.repository.EmployeeRepository;

import jakarta.transaction.Transactional;

import java.util.*;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private AddressRepository addressRepository;
	

	@Transactional
	public Employee addEmployeeDetails(Employee employee) {
		System.out.println("Added Employee: " + employee.getEmpName());

		if (employee.getAddress() != null) {
			Optional<Address> existingAddress = addressRepository.findByCityAndStreet(employee.getAddress().getCity(),
					employee.getAddress().getStreet());

			if (existingAddress.isPresent()) {
				employee.setAddress(existingAddress.get()); // Assign existing address
			} else {
				Address savedAddress = addressRepository.save(employee.getAddress()); // Save new address
				employee.setAddress(savedAddress);
			}
		}

		return employeeRepository.save(employee); // Save Employee with Address reference
	}

	public List<Employee> viewAllEmployee() {
		return employeeRepository.findAll(); // Returns an empty list if no data exists
	}

	public Employee viewEmployee(Long id) {
		return employeeRepository.findById(id).orElse(null); // Returns an empty list if no data exists
	}

//    Since this method updates an entity and involves multiple database operations, it's best to use @Transactional to maintain consistency.
	@Transactional
	public Employee updateEmployee(Employee employee, Long id) {

		// Find existing employee by ID
		Employee existingEmployee = employeeRepository.findById(id).orElse(null);

		if (existingEmployee == null) {
			return null;
		}

		// Check if Address is provided
		if (employee.getAddress() != null) {
			Optional<Address> existingAddress = addressRepository.findByCityAndStreet(employee.getAddress().getCity(),
					employee.getAddress().getStreet());

			if (existingAddress.isPresent()) {
				existingEmployee.setAddress(existingAddress.get()); // Assign existing address
			} else {
				Address savedAddress = addressRepository.saveAndFlush(employee.getAddress()); // Save new address
				existingEmployee.setAddress(savedAddress);
			}
		}

		// Update fields selectively to avoid overwriting with null values
		if (employee.getEmpName() != null) {
			existingEmployee.setEmpName(employee.getEmpName());
		}
		if (employee.getEmpMobile() != null) {
			existingEmployee.setEmpMobile(employee.getEmpMobile());
		}
		if (employee.getEmpSalary() > 0) { // Assuming salary can't be zero
			existingEmployee.setEmpSalary(employee.getEmpSalary());
		}

		return employeeRepository.saveAndFlush(existingEmployee);
	}

	@Transactional
	public String deleteEmployee(Long id) {

		// Find existing employee by ID
		Employee employee = employeeRepository.findById(id).orElse(null);

		if (employee == null) {
			return null;
		}

		Long addressId = (employee.getAddress() != null) ? employee.getAddress().getId() : null;
	

		if (addressId != null) {
			// Find all employees sharing the same address_id
			List<Employee> employeesWithSameAddress = employeeRepository.findByAddress_Id(addressId);

			if (employeesWithSameAddress.size() == 1) { // Only one employee with this address exists
				employeeRepository.deleteById(id);
				addressRepository.deleteById(addressId); // Delete address
				return "Employee with ID " + id + " deleted successfully!";
			} else {

				// Delete employee
				employeeRepository.deleteById(id);
				return "Employee with ID " + id + " deleted successfully!";
			}

		} else {
			return null;
		}

	}
	
	@Transactional
	public void deleteAllEmployee() {
		employeeRepository.deleteAll();
		addressRepository.deleteAll();
	}

}
