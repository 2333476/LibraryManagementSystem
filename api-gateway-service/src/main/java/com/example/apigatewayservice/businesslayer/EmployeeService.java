package com.example.apigatewayservice.businesslayer;

import com.example.apigatewayservice.presentationlayer.employeedto.EmployeeRequestModel;
import com.example.apigatewayservice.presentationlayer.employeedto.EmployeeResponseModel;

import java.util.List;

public interface EmployeeService {
    List<EmployeeResponseModel> getEmployees();

    EmployeeResponseModel getEmployeeByEmployeeId(String employeeId);

    EmployeeResponseModel addEmployee(EmployeeRequestModel employeeRequestModel);

    EmployeeResponseModel updateEmployee(String employeeId, EmployeeRequestModel requestModel);


    void deleteEmployee(String employeeId);


}
