package com.tcs.Practice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcs.Practice.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
	// Find employees by address_id
	List<Employee> findByAddress_Id(Long addressId);
}
