package com.example.apigateway.presentationlayer.employeedto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseModel extends RepresentationModel<EmployeeResponseModel> {

    private String employeeId;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private Double salary;
    private Double commissionRate;
    private Department department;
    private String streetAddress;
    private String city;
    private String province;
    private String country;
    private String postalCode;
}
