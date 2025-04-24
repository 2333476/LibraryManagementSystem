package com.example.apigateway.businesslayer;

import com.example.apigateway.domainclientlayer.EmployeesServiceClient;
import com.example.apigateway.presentationlayer.EmployeeController;
import com.example.apigateway.presentationlayer.employeedto.EmployeeRequestModel;
import com.example.apigateway.presentationlayer.employeedto.EmployeeResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeesServiceClient employeesServiceClient;

    public EmployeeServiceImpl(EmployeesServiceClient employeesServiceClient) {
        this.employeesServiceClient = employeesServiceClient;
    }

    @Override
    public List<EmployeeResponseModel> getEmployees() {
        List<EmployeeResponseModel> employees = employeesServiceClient.getAllEmployees();
        employees.forEach(this::addLinks);
        return employees;
    }

    @Override
    public EmployeeResponseModel getEmployeeByEmployeeId(String employeeId) {
        EmployeeResponseModel model = this.employeesServiceClient.getEmployeeByEmployeeId(employeeId);

        model.add(linkTo(methodOn(EmployeeController.class).getEmployeeByEmployeeId(employeeId)).withSelfRel());
        model.add(linkTo(methodOn(EmployeeController.class).getEmployees()).withRel("all-employees"));

        return model;
    }


    @Override
    public EmployeeResponseModel addEmployee(EmployeeRequestModel postData) {
        EmployeeResponseModel createdEmployee = employeesServiceClient.addEmployee(postData);
        return addLinks(createdEmployee);
    }

    @Override
    public EmployeeResponseModel updateEmployee(String employeeId, EmployeeRequestModel postData) {
        EmployeeResponseModel updatedEmployee = employeesServiceClient.updateEmployee(employeeId, postData);
        return addLinks(updatedEmployee);
    }

    @Override
    public void deleteEmployee(String employeeId) {
        employeesServiceClient.deleteEmployee(employeeId);
    }

    private EmployeeResponseModel addLinks(EmployeeResponseModel employee) {
        String id = employee.getEmployeeId();
        employee.add(linkTo(methodOn(EmployeeController.class).getEmployeeByEmployeeId(id)).withSelfRel());
        employee.add(linkTo(methodOn(EmployeeController.class).updateEmployee(id, null)).withRel("update"));
        employee.add(linkTo(methodOn(EmployeeController.class).deleteEmployee(id)).withRel("delete"));
        return employee;
    }
}
