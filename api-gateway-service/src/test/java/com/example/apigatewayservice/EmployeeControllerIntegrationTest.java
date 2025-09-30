package com.example.apigatewayservice;
import com.example.apigatewayservice.domainclientlayer.EmployeeServiceClient;
import com.example.apigatewayservice.presentationlayer.employeedto.Department;
import com.example.apigatewayservice.presentationlayer.employeedto.EmployeeResponseModel;
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
public class EmployeeControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private EmployeeServiceClient employeeServiceClient;

    @Test
    void getAllEmployees_ReturnsOkWithEmployeesList() throws Exception {
        EmployeeResponseModel mockEmployee = EmployeeResponseModel.builder()
                .employeeId("emp001")
                .firstName("John")
                .lastName("Doe")
                .emailAddress("john.doe@example.com")
                .phoneNumber("555-5555")
                .salary(75000.00)
                .commissionRate(0.15)
                .department(Department.Sales)
                .streetAddress("100 Business Rd")
                .city("Montreal")
                .province("QC")
                .country("Canada")
                .postalCode("H1A2B3")
                .build();

        when(employeeServiceClient.getAllEmployees()).thenReturn(List.of(mockEmployee));

        mockMvc.perform(get("/api/v1/employees")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].employeeId").value("emp001"))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].department").value("Sales"))
                .andExpect(jsonPath("$[0].salary").value(75000.00));
    }
}
