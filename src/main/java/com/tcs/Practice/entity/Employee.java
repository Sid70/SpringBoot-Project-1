package com.tcs.Practice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id", nullable = false, unique = true)
    private Long empId;

    @Column(name = "emp_name", nullable = false, length = 100)
    private String empName;

    @Column(name = "emp_mobile", nullable = false, length = 15)
    private String empMobile;

    @Column(name = "emp_salary", nullable = false)
    private double empSalary;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

	// Constructors
    public Employee() {}

    public Employee(String empName, String empMobile, double empSalary, Address address) {
        this.empName = empName;
        this.empMobile = empMobile;
        this.empSalary = empSalary;
        this.address = address;
    }

	// Getters and Setters
    public Long getEmpId() {
        return empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpMobile() {
        return empMobile;
    }

    public void setEmpMobile(String empMobile) {
        this.empMobile = empMobile;
    }

    public double getEmpSalary() {
        return empSalary;
    }

    public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public void setEmpSalary(double empSalary) {
        this.empSalary = empSalary;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
