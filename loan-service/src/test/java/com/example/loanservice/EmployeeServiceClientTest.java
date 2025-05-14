package com.example.loanservice;

import com.example.loanservice.datamapperlayer.employeedto.EmployeeResponseModel;
import com.example.loanservice.domainclientlayer.EmployeeServiceClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceClientTest {

    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final EmployeeServiceClient client =
            new EmployeeServiceClient(restTemplate, objectMapper, "localhost", "8082");

    @Test
    void shouldThrowExceptionWhenEmployeeNotFound() {
        String id = "invalid-id";
        when(restTemplate.getForObject("http://localhost:8082/api/v1/employees/" + id, EmployeeResponseModel.class))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        assertThrows(RuntimeException.class, () -> client.getEmployeeByEmployeeId(id));
    }

    @Test
    void shouldReturnEmployeeWhenFound() {
        String id = "valid-id";
        EmployeeResponseModel mockEmployee = new EmployeeResponseModel();
        mockEmployee.setEmployeeId(id);
        mockEmployee.setFirstName("John");
        mockEmployee.setLastName("Doe");

        when(restTemplate.getForObject("http://localhost:8082/api/v1/employees/" + id, EmployeeResponseModel.class))
                .thenReturn(mockEmployee);

        EmployeeResponseModel result = client.getEmployeeByEmployeeId(id);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
    }
}
