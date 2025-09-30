package com.example.apigatewayservice;
import com.example.apigatewayservice.domainclientlayer.CustomerServiceClient;
import com.example.apigatewayservice.presentationlayer.customerdto.CustomerResponseModel;
import com.example.apigatewayservice.presentationlayer.customerdto.PhoneNumber;
import com.example.apigatewayservice.presentationlayer.customerdto.PhoneType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private CustomerServiceClient customerServiceClient;

    @Test
    void getAllCustomers_ReturnsOkWithCustomersList() throws Exception {
        // Prepare mock data
        PhoneNumber phone1 = new PhoneNumber(PhoneType.MOBILE, "555-1234-1234");
        PhoneNumber phone2 = new PhoneNumber(PhoneType.WORK, "555-5678-5123");

        CustomerResponseModel mockCustomer = CustomerResponseModel.builder()
                .customerId("cust001")
                .firstName("Alice")
                .lastName("Smith")
                .emailAddress("alice@example.com")
                .streetAddress("123 Main St")
                .city("Montreal")
                .province("QC")
                .country("Canada")
                .postalCode("H1A2B3")
                .phoneNumbers(List.of(phone1, phone2))
                .build();

        when(customerServiceClient.getAllCustomers()).thenReturn(List.of(mockCustomer));

        // Perform GET request and verify
        mockMvc.perform(get("/api/v1/customers")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerId").value("cust001"))
                .andExpect(jsonPath("$[0].firstName").value("Alice"))
                .andExpect(jsonPath("$[0].phoneNumbers[0].type").value(PhoneType.MOBILE.toString()))
                .andExpect(jsonPath("$[0].phoneNumbers[0].number").value("555-1234-1234"));
    }
}
