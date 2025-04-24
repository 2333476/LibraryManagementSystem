package com.example.apigatewayservice.domainclientlayer;

import com.example.apigatewayservice.presentationlayer.employeedto.EmployeeRequestModel;
import com.example.apigatewayservice.presentationlayer.employeedto.EmployeeResponseModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Component
public class EmployeeServiceClient {

    private final RestTemplate restTemplate;
    private final String EMPLOYEE_SERVICE_BASE_URL;

    public EmployeeServiceClient(RestTemplate restTemplate,
                                 @Value("${app.employee-service.host}") String employeesServiceHost,
                                 @Value("${app.employee-service.port}") String employeesServicePort) {
        this.restTemplate = restTemplate;
        this.EMPLOYEE_SERVICE_BASE_URL = "http://" + employeesServiceHost + ":" + employeesServicePort + "/api/v1/employees";
    }

    public List<EmployeeResponseModel> getAllEmployees() {
        try {
            return restTemplate.exchange(
                    EMPLOYEE_SERVICE_BASE_URL,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<EmployeeResponseModel>>() {}
            ).getBody();
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Erreur lors de l'appel à employee-service: " + e.getMessage());
        }
    }


    public EmployeeResponseModel getEmployeeByEmployeeId(String employeeId) {
        try {
            return restTemplate.exchange(
                    EMPLOYEE_SERVICE_BASE_URL + "/" + employeeId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<EmployeeResponseModel>() {}
            ).getBody();
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Erreur lors de l'appel à employee-service pour l'ID: " + employeeId + " - " + e.getMessage());
        }
    }


    public EmployeeResponseModel addEmployee(EmployeeRequestModel employeeRequestModel) {
        try {
            return restTemplate.postForObject(
                    EMPLOYEE_SERVICE_BASE_URL,
                    employeeRequestModel,
                    EmployeeResponseModel.class
            );
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Erreur lors de l'ajout d’un employé : " + e.getMessage());
        }
    }


    public EmployeeResponseModel updateEmployee(String employeeId, EmployeeRequestModel requestModel) {
        String url = EMPLOYEE_SERVICE_BASE_URL + "/" + employeeId;
        try {
            return restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    new HttpEntity<>(requestModel),
                    EmployeeResponseModel.class
            ).getBody();
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de l'employé : " + e.getMessage());
        }
    }


    public void deleteEmployee(String employeeId) {
        String url = EMPLOYEE_SERVICE_BASE_URL + "/" + employeeId;
        try {
            restTemplate.exchange(
                    url,
                    HttpMethod.DELETE,
                    null,
                    Void.class
            );
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Erreur lors de la suppression de l'employé : " + e.getMessage());
        }
    }




}
