package com.example.apigatewayservice;

import com.example.apigatewayservice.businesslayer.CustomerService;
import com.example.apigatewayservice.presentationlayer.CustomerController;
import com.example.apigatewayservice.presentationlayer.customerdto.CustomerRequestModel;
import com.example.apigatewayservice.presentationlayer.customerdto.CustomerResponseModel;
import com.example.apigatewayservice.presentationlayer.customerdto.PhoneNumber;
import com.example.apigatewayservice.presentationlayer.customerdto.PhoneType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerControllerTest {
    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private List<PhoneNumber> mockPhoneNumbers;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerController = new CustomerController(customerService);
        mockPhoneNumbers = List.of(new PhoneNumber(PhoneType.WORK, "5141234567"));
    }

    @Test
    void testGetAllCustomers() {
        List<CustomerResponseModel> mockList = List.of(
                CustomerResponseModel.builder()
                        .customerId("cust1")
                        .firstName("John")
                        .lastName("Doe")
                        .emailAddress("john.doe@example.com")
                        .streetAddress("123 Main St")
                        .city("Montreal")
                        .province("QC")
                        .country("Canada")
                        .postalCode("H2X 1Y4")
                        .phoneNumbers(mockPhoneNumbers)
                        .build()
        );

        when(customerService.getCustomers()).thenReturn(mockList);

        ResponseEntity<List<CustomerResponseModel>> response = customerController.getAllCustomers();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("cust1", response.getBody().get(0).getCustomerId());
        verify(customerService).getCustomers();
    }

    @Test
    void testGetCustomerById() {
        String customerId = "cust42";

        CustomerResponseModel mockCustomer = CustomerResponseModel.builder()
                .customerId(customerId)
                .firstName("Jane")
                .lastName("Smith")
                .emailAddress("jane@example.com")
                .streetAddress("456 Elm St")
                .city("Laval")
                .province("QC")
                .country("Canada")
                .postalCode("H7A 2Z3")
                .phoneNumbers(mockPhoneNumbers)
                .build();

        when(customerService.getCustomerByCustomerId(customerId)).thenReturn(mockCustomer);

        ResponseEntity<CustomerResponseModel> response = customerController.getCustomerById(customerId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("cust42", response.getBody().getCustomerId());
        verify(customerService).getCustomerByCustomerId(customerId);
    }

    @Test
    void testAddCustomer() {
        CustomerRequestModel request = new CustomerRequestModel(
                "Alice", "Johnson", "alice@example.com",
                "789 Pine St", "Quebec City", "QC", "Canada", "G1K 3A1", mockPhoneNumbers
        );

        CustomerResponseModel mockResponse = CustomerResponseModel.builder()
                .customerId("cust99")
                .firstName("Alice")
                .lastName("Johnson")
                .emailAddress("alice@example.com")
                .streetAddress("789 Pine St")
                .city("Quebec City")
                .province("QC")
                .country("Canada")
                .postalCode("G1K 3A1")
                .phoneNumbers(mockPhoneNumbers)
                .build();

        when(customerService.addCustomer(request)).thenReturn(mockResponse);

        ResponseEntity<CustomerResponseModel> response = customerController.addCustomer(request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("cust99", response.getBody().getCustomerId());
        verify(customerService).addCustomer(request);
    }

    @Test
    void testUpdateCustomer() {
        String customerId = "cust-update";
        CustomerRequestModel request = new CustomerRequestModel(
                "Updated", "Name", "updated@example.com",
                "100 Updated St", "Sherbrooke", "QC", "Canada", "J1H 4A1", mockPhoneNumbers
        );

        CustomerResponseModel updated = CustomerResponseModel.builder()
                .customerId(customerId)
                .firstName("Updated")
                .lastName("Name")
                .emailAddress("updated@example.com")
                .streetAddress("100 Updated St")
                .city("Sherbrooke")
                .province("QC")
                .country("Canada")
                .postalCode("J1H 4A1")
                .phoneNumbers(mockPhoneNumbers)
                .build();

        when(customerService.updateCustomer(customerId, request)).thenReturn(updated);

        ResponseEntity<CustomerResponseModel> response = customerController.updateCustomer(customerId, request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Updated", response.getBody().getFirstName());
        verify(customerService).updateCustomer(customerId, request);
    }

    @Test
    void testDeleteCustomer() {
        String customerId = "cust-delete";
        String message = "Customer deleted.";

        when(customerService.deleteCustomerByCustomerId(customerId)).thenReturn(message);

        ResponseEntity<String> response = customerController.deleteCustomerById(customerId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(message, response.getBody());
        verify(customerService).deleteCustomerByCustomerId(customerId);
    }
}
