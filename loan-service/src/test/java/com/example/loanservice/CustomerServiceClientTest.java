package com.example.loanservice;

import com.example.loanservice.datamapperlayer.customerdto.CustomerResponseModel;
import com.example.loanservice.domainclientlayer.CustomerServiceClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceClientTest {

    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final CustomerServiceClient client =
            new CustomerServiceClient(restTemplate, objectMapper, "localhost", "8081");

    @Test
    void shouldThrowExceptionWhenCustomerNotFound() {
        String id = "invalid-id";
        when(restTemplate.getForObject("http://localhost:8081/api/v1/customers/" + id, CustomerResponseModel.class))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        assertThrows(RuntimeException.class, () -> client.getCustomerByCustomerId(id));
    }

    @Test
    void shouldReturnCustomerWhenFound() {
        String id = "valid-id";
        CustomerResponseModel mockCustomer = new CustomerResponseModel();
        mockCustomer.setCustomerId(id);
        mockCustomer.setFirstName("Alice");
        mockCustomer.setLastName("Smith");

        when(restTemplate.getForObject("http://localhost:8081/api/v1/customers/" + id, CustomerResponseModel.class))
                .thenReturn(mockCustomer);

        CustomerResponseModel result = client.getCustomerByCustomerId(id);
        assertEquals("Alice", result.getFirstName());
        assertEquals("Smith", result.getLastName());
    }
}
