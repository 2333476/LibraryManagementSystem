package com.example.apigatewayservice;

import com.example.apigatewayservice.businesslayer.CustomerService;
import com.example.apigatewayservice.presentationlayer.CustomerController;
import com.example.apigatewayservice.presentationlayer.customerdto.CustomerRequestModel;
import com.example.apigatewayservice.presentationlayer.customerdto.CustomerResponseModel;
import com.example.apigatewayservice.presentationlayer.customerdto.PhoneNumber;
import com.example.apigatewayservice.presentationlayer.customerdto.PhoneType;
import com.example.apigatewayservice.utils.exceptions.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
public class CustomerControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("✔ GET all customers returns 200 OK with list")
    void getAllCustomers_returnsOk() throws Exception {
        CustomerResponseModel customer = CustomerResponseModel.builder()
                .customerId("123")
                .firstName("John")
                .lastName("Doe")
                .emailAddress("john@example.com")
                .streetAddress("123 St")
                .city("Montreal")
                .province("QC")
                .country("Canada")
                .postalCode("H1A1A1")
                .phoneNumbers(List.of(new PhoneNumber(PhoneType.MOBILE, "514-111-2222")))
                .build();

        Mockito.when(customerService.getCustomers()).thenReturn(List.of(customer));

        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"));
    }

    @Test
    @DisplayName("✔ GET customer by ID returns 200 OK")
    void getCustomerById_returnsOk() throws Exception {
        String customerId = "123";

        CustomerResponseModel customer = CustomerResponseModel.builder()
                .customerId(customerId)
                .firstName("Alice")
                .lastName("Smith")
                .emailAddress("alice@example.com")
                .streetAddress("456 St")
                .city("Quebec City")
                .province("QC")
                .country("Canada")
                .postalCode("G1A1A1")
                .phoneNumbers(List.of(new PhoneNumber(PhoneType.WORK, "514-222-3333")))
                .build();

        Mockito.when(customerService.getCustomerByCustomerId(customerId)).thenReturn(customer);

        mockMvc.perform(get("/api/v1/customers/{id}", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Alice"));
    }

    @Test
    @DisplayName("✔ POST valid customer returns 200 OK")
    void addCustomer_returnsOk() throws Exception {
        CustomerRequestModel request = new CustomerRequestModel(
                "Bob", "Test", "bob@test.com", "789 King",
                "Toronto", "ON", "Canada", "M5V2N8",
                List.of(new PhoneNumber(PhoneType.HOME, "514-333-4444"))
        );

        CustomerResponseModel response = CustomerResponseModel.builder()
                .customerId("456")
                .firstName("Bob")
                .lastName("Test")
                .emailAddress("bob@test.com")
                .streetAddress("789 King")
                .city("Toronto")
                .province("ON")
                .country("Canada")
                .postalCode("M5V2N8")
                .phoneNumbers(List.of(new PhoneNumber(PhoneType.HOME, "514-333-4444")))
                .build();

        Mockito.when(customerService.addCustomer(any(CustomerRequestModel.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Bob"));
    }

    @Test
    @DisplayName("✔ DELETE customer returns 200 OK")
    void deleteCustomer_returnsOk() throws Exception {
        // If the method is void and does nothing, no need to mock
        mockMvc.perform(delete("/api/v1/customers/{id}", "789"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("✖ GET customer by invalid ID returns 404")
    void getCustomerByInvalidId_returnsNotFound() throws Exception {
        Mockito.when(customerService.getCustomerByCustomerId("invalid-id"))
                .thenThrow(new NotFoundException("Customer not found")); // use your actual exception

        mockMvc.perform(get("/api/v1/customers/invalid-id"))
                .andExpect(status().isNotFound()); // works if your exception handler maps NotFoundException to 404
    }
}
