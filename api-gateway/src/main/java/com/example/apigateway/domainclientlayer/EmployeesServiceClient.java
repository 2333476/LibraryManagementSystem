package com.example.apigateway.domainclientlayer;

import com.example.apigateway.presentationlayer.employeedto.EmployeeRequestModel;
import com.example.apigateway.presentationlayer.employeedto.EmployeeResponseModel;
import com.example.apigateway.utils.exception.InvalidInputException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;


@Component
@Slf4j
public class EmployeesServiceClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String EMPLOYEE_SERVICE_BASE_URL;



    public EmployeesServiceClient(RestTemplate restTemplate,
                                  ObjectMapper objectMapper,
                                  @Value("${app.employees-service.host}") String employeesServiceHost,
                                  @Value("${app.employees-service.port}") String employeesServicePort) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.EMPLOYEE_SERVICE_BASE_URL = "http://" + employeesServiceHost + ":" + employeesServicePort + "/api/v1/employees";
    }



    public List<EmployeeResponseModel> getAllEmployees() {
        try {
            List<EmployeeResponseModel> employeeResponseModels =
                    this.restTemplate.exchange(
                            this.EMPLOYEE_SERVICE_BASE_URL,
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<List<EmployeeResponseModel>>() {}
                    ).getBody();

            return employeeResponseModels;
        } catch (HttpClientErrorException e){
            throw handleHttpClientException(e);
        }
    }



    public EmployeeResponseModel getEmployeeByEmployeeId(String employeeId) {
        try {
            EmployeeResponseModel response = this.restTemplate.getForObject(
                    this.EMPLOYEE_SERVICE_BASE_URL + "/" + employeeId,
                    EmployeeResponseModel.class
            );
            return response;
        } catch (HttpClientErrorException e) {
            throw handleHttpClientException(e);
        }
    }


    public EmployeeResponseModel addEmployee(EmployeeRequestModel postData) {
        try {
            return this.restTemplate.postForObject(
                    this.EMPLOYEE_SERVICE_BASE_URL,  // URL du service cible
                    postData,                        // Corps de la requête : ce qu'on envoie
                    EmployeeResponseModel.class      // Type de réponse attendue
            );
        } catch (HttpClientErrorException e) {
            throw handleHttpClientException(e);
        }
    }



    public EmployeeResponseModel updateEmployee(String employeeId, EmployeeRequestModel postData) {
        try {
            this.restTemplate.put(this.EMPLOYEE_SERVICE_BASE_URL + "/" + employeeId, postData);
            return getEmployeeByEmployeeId(employeeId);
        } catch (HttpClientErrorException e) {
            throw handleHttpClientException(e);
        }
    }


    public void deleteEmployee(String employeeId) {
        try {
            this.restTemplate.delete(this.EMPLOYEE_SERVICE_BASE_URL + "/" + employeeId);
        } catch (HttpClientErrorException e) {
            throw handleHttpClientException(e);
        }
    }



    private RuntimeException handleHttpClientException(HttpClientErrorException ex){

        if (ex.getStatusCode()==UNPROCESSABLE_ENTITY){
            throw new InvalidInputException();
        }
        else if (ex.getStatusCode()==NOT_FOUND){
            throw new com.example.apigateway.utils.exceptions.NotFoundException();
        }

        return ex;

    }





}
