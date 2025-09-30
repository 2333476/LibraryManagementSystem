package com.example.apigatewayservice;
import com.example.apigatewayservice.businesslayer.CustomerServiceImpl;
import com.example.apigatewayservice.domainclientlayer.CustomerServiceClient;
import com.example.apigatewayservice.presentationlayer.customerdto.CustomerRequestModel;
import com.example.apigatewayservice.presentationlayer.customerdto.CustomerResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class CustomerServiceImplTest {
    private CustomerServiceClient customerServiceClient;
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        customerServiceClient = mock(CustomerServiceClient.class);
        customerService = new CustomerServiceImpl(customerServiceClient);
    }

    @Test
    void getCustomers_shouldReturnListOfCustomers() {
        // Arrange
        CustomerResponseModel customer = new CustomerResponseModel();
        List<CustomerResponseModel> expectedList = Arrays.asList(customer);
        when(customerServiceClient.getAllCustomers()).thenReturn(expectedList);

        // Act
        List<CustomerResponseModel> actualList = customerService.getCustomers();

        // Assert
        assertEquals(expectedList, actualList);
        verify(customerServiceClient, times(1)).getAllCustomers();
    }

    @Test
    void getCustomerByCustomerId_shouldReturnCustomer() {
        // Arrange
        String customerId = "cust123";
        CustomerResponseModel expected = new CustomerResponseModel();
        when(customerServiceClient.getCustomerById(customerId)).thenReturn(expected);

        // Act
        CustomerResponseModel actual = customerService.getCustomerByCustomerId(customerId);

        // Assert
        assertEquals(expected, actual);
        verify(customerServiceClient).getCustomerById(customerId);
    }

    @Test
    void addCustomer_shouldReturnCreatedCustomer() {
        // Arrange
        CustomerRequestModel request = new CustomerRequestModel();
        CustomerResponseModel expected = new CustomerResponseModel();
        when(customerServiceClient.addCustomer(request)).thenReturn(expected);

        // Act
        CustomerResponseModel actual = customerService.addCustomer(request);

        // Assert
        assertEquals(expected, actual);
        verify(customerServiceClient).addCustomer(request);
    }

    @Test
    void updateCustomer_shouldReturnUpdatedCustomer() {
        // Arrange
        String customerId = "cust456";
        CustomerRequestModel request = new CustomerRequestModel();
        CustomerResponseModel expected = new CustomerResponseModel();
        when(customerServiceClient.updateCustomer(customerId, request)).thenReturn(expected);

        // Act
        CustomerResponseModel actual = customerService.updateCustomer(customerId, request);

        // Assert
        assertEquals(expected, actual);
        verify(customerServiceClient).updateCustomer(customerId, request);
    }

    @Test
    void deleteCustomerByCustomerId_shouldReturnConfirmationMessage() {
        // Arrange
        String customerId = "cust789";
        String expected = "Customer deleted";
        when(customerServiceClient.deleteCustomerById(customerId)).thenReturn(expected);

        // Act
        String actual = customerService.deleteCustomerByCustomerId(customerId);

        // Assert
        assertEquals(expected, actual);
        verify(customerServiceClient).deleteCustomerById(customerId);
    }
}
