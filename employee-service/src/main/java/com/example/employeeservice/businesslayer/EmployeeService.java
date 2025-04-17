package com.example.employeeservice.businesslayer;





import com.example.employeeservice.presentationlayer.EmployeeRequestModel;
import com.example.employeeservice.presentationlayer.EmployeeResponseModel;

import java.util.List;

public interface EmployeeService {


    public List<EmployeeResponseModel> getEmployees();

    EmployeeResponseModel getEmployeeByEmployeeId(String employeeId);

    public EmployeeResponseModel addEmployee(EmployeeRequestModel postData);

    EmployeeResponseModel updateEmployee(String employeeId, EmployeeRequestModel postData);
}
