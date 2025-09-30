package com.example.apigatewayservice;
import com.example.apigatewayservice.domainclientlayer.CustomerServiceClient;
import com.example.apigatewayservice.presentationlayer.customerdto.CustomerRequestModel;
import com.example.apigatewayservice.presentationlayer.customerdto.CustomerResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceClientTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CustomerServiceClient customerServiceClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerServiceClient = new CustomerServiceClient(restTemplate, "localhost", "8081");
    }

    @Test
    void testGetAllCustomers() {
        List<CustomerResponseModel> mockList = List.of(
                new CustomerResponseModel(/* provide constructor values if needed */)
        );

        ParameterizedTypeReference<List<CustomerResponseModel>> typeRef =
                new ParameterizedTypeReference<>() {};

        ResponseEntity<List<CustomerResponseModel>> response = new ResponseEntity<>(mockList, HttpStatus.OK);

        when(restTemplate.exchange(
                eq("http://localhost:8081/api/v1/customers"),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class)
        )).thenReturn(response);

        List<CustomerResponseModel> result = customerServiceClient.getAllCustomers();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetCustomerById() {
        String id = "123";
        CustomerResponseModel mockResponse = new CustomerResponseModel(/* constructor args */);

        when(restTemplate.getForObject(contains(id), eq(CustomerResponseModel.class)))
                .thenReturn(mockResponse);

        CustomerResponseModel result = customerServiceClient.getCustomerById(id);

        assertNotNull(result);
    }

    @Test
    void testAddCustomer() {
        CustomerRequestModel request = new CustomerRequestModel();
        CustomerResponseModel response = new CustomerResponseModel(/* constructor args */);

        when(restTemplate.postForObject(anyString(), eq(request), eq(CustomerResponseModel.class)))
                .thenReturn(response);

        CustomerResponseModel result = customerServiceClient.addCustomer(request);

        assertNotNull(result);
    }

    @Test
    void testUpdateCustomer() {
        String id = "321";
        CustomerRequestModel request = new CustomerRequestModel();
        CustomerResponseModel mockResult = new CustomerResponseModel(/* constructor args */);

        doNothing().when(restTemplate).put(contains(id), eq(request));
        when(restTemplate.getForObject(contains(id), eq(CustomerResponseModel.class))).thenReturn(mockResult);

        CustomerResponseModel result = customerServiceClient.updateCustomer(id, request);

        assertNotNull(result);
        verify(restTemplate).put(contains(id), eq(request));
    }

    @Test
    void testDeleteCustomerById() {
        String id = "delete-me";

        doNothing().when(restTemplate).delete(contains(id));

        String result = customerServiceClient.deleteCustomerById(id);

        assertEquals("Customer with ID delete-me has been deleted successfully.", result);
    }
}
