package com.example.apigatewayservice.businesslayer;

import com.example.apigatewayservice.domainclientlayer.EmployeeServiceClient;
import com.example.apigatewayservice.presentationlayer.employeedto.EmployeeRequestModel;
import com.example.apigatewayservice.presentationlayer.employeedto.EmployeeResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeServiceClient employeeServiceClient;

    public EmployeeServiceImpl(EmployeeServiceClient employeeServiceClient) {
        this.employeeServiceClient = employeeServiceClient;
    }

    @Override
    public List<EmployeeResponseModel> getEmployees() {
        return employeeServiceClient.getAllEmployees();
    }


    @Override
    public EmployeeResponseModel getEmployeeByEmployeeId(String employeeId) {
        return employeeServiceClient.getEmployeeByEmployeeId(employeeId);
    }

    @Override
    public EmployeeResponseModel addEmployee(EmployeeRequestModel employeeRequestModel) {
        return employeeServiceClient.addEmployee(employeeRequestModel);
    }


    @Override
    public EmployeeResponseModel updateEmployee(String employeeId, EmployeeRequestModel requestModel) {
        return employeeServiceClient.updateEmployee(employeeId, requestModel);
    }


    @Override
    public void deleteEmployee(String employeeId) {
        employeeServiceClient.deleteEmployee(employeeId);
    }



}
