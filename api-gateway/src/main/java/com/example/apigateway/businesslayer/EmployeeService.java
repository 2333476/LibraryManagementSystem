package com.example.apigateway.businesslayer;

import com.example.apigateway.presentationlayer.employeedto.EmployeeRequestModel;
import com.example.apigateway.presentationlayer.employeedto.EmployeeResponseModel;

import java.util.List;

public interface EmployeeService {
    List<EmployeeResponseModel> getEmployees();

    EmployeeResponseModel getEmployeeByEmployeeId(String employeeId);

    EmployeeResponseModel addEmployee(EmployeeRequestModel postData);

    EmployeeResponseModel updateEmployee(String employeeId, EmployeeRequestModel postData);

    void deleteEmployee(String employeeId);
}
