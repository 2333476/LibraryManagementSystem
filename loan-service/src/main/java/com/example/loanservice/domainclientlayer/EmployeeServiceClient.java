package com.example.loanservice.domainclientlayer;

import com.example.loanservice.datamapperlayer.employeedto.EmployeeResponseModel;
import com.example.loanservice.utils.exceptions.InvalidInputException;
import com.example.loanservice.utils.exceptions.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpStatus.*;

@Component
@Slf4j
public class EmployeeServiceClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String EMPLOYEE_SERVICE_BASE_URL;

    public EmployeeServiceClient(RestTemplate restTemplate,
                                 ObjectMapper objectMapper,
                                 @Value("${app.employee-service.host}") String host,
                                 @Value("${app.employee-service.port}") String port) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.EMPLOYEE_SERVICE_BASE_URL = "http://" + host + ":" + port + "/api/v1/employees";
    }

    public EmployeeResponseModel getEmployeeByEmployeeId(String employeeId) {
        try {
            return restTemplate.getForObject(
                    EMPLOYEE_SERVICE_BASE_URL + "/" + employeeId,
                    EmployeeResponseModel.class
            );
        } catch (HttpClientErrorException e) {
            throw handleHttpClientException(e);
        }
    }

    private RuntimeException handleHttpClientException(HttpClientErrorException ex) {
        if (ex.getStatusCode() == UNPROCESSABLE_ENTITY) {
            throw new InvalidInputException();
        } else if (ex.getStatusCode() == NOT_FOUND) {
            throw new NotFoundException();
        }
        return ex;
    }
}
