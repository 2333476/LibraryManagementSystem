package com.example.employeeservice.datalayer;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="employees")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //private identifier

    @Embedded
    private EmployeeIdentifier employeeIdentifier; //public identifier

    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;

    private Double salary;
    private Double commissionRate;

    @Enumerated(EnumType.STRING)
    private Department department;

    @Embedded
    private Address address;

    public Employee() {
        this.employeeIdentifier = new EmployeeIdentifier();
    }

    public Employee(EmployeeIdentifier employeeIdentifier) {
        this.employeeIdentifier = employeeIdentifier;
    }

    public Employee(EmployeeIdentifier employeeIdentifier,
                    String firstName, String lastName, String emailAddress,
                    String phoneNumber,
                    Double salary, Double commissionRate,
                    Department department, Address address) {

        this.employeeIdentifier = employeeIdentifier;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
        this.commissionRate = commissionRate;
        this.department = department;
        this.address = address;
    }
}

