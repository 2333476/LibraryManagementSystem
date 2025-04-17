package com.example.employeeservice.datalayer;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class EmployeeIdentifier {

    private String employeeId;

    public EmployeeIdentifier() {
        this.employeeId = UUID.randomUUID().toString();
    }

    public String getEmployeeId() {
        return employeeId;
    }
}
