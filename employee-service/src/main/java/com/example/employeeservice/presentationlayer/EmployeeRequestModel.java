package com.example.employeeservice.presentationlayer;


import com.example.employeeservice.datalayer.Department;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeRequestModel {

    private final String firstName;
    private final String lastName;
    private final String emailAddress;
    private final String phoneNumber;

    private final Double salary;
    private final Double commissionRate;

    private final Department department;
    private final String streetAddress;
    private final String city;
    private final String province;
    private final String country;
    private final String postalCode;

}