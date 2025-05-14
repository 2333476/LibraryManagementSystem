package com.example.loanservice.datamapperlayer.employeedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class EmployeeResponseModel {

    private String employeeId;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private Double salary;
    private Double commissionRate;
    private String department;
}
