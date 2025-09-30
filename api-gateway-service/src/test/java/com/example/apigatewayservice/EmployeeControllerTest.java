package com.example.apigatewayservice;
import com.example.apigatewayservice.businesslayer.EmployeeService;
import com.example.apigatewayservice.presentationlayer.EmployeeController;
import com.example.apigatewayservice.presentationlayer.employeedto.Department;
import com.example.apigatewayservice.presentationlayer.employeedto.EmployeeRequestModel;
import com.example.apigatewayservice.presentationlayer.employeedto.EmployeeResponseModel;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private EmployeeService employeeService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    EmployeeResponseModel sampleResponse = EmployeeResponseModel.builder()
            .employeeId("1")
            .firstName("John")
            .lastName("Doe")
            .emailAddress("john@example.com")
            .phoneNumber("1234567890")
            .salary(50000.0)
            .commissionRate(0.1)
            .department(Department.Sales)
            .streetAddress("123 Main St")
            .city("City")
            .province("Province")
            .country("Country")
            .postalCode("12345")
            .build();

    EmployeeRequestModel sampleRequest = EmployeeRequestModel.builder()
            .firstName("John")
            .lastName("Doe")
            .emailAddress("john@example.com")
            .phoneNumber("1234567890")
            .salary(50000.0)
            .commissionRate(0.1)
            .department(Department.Sales)
            .streetAddress("123 Main St")
            .city("City")
            .province("Province")
            .country("Country")
            .postalCode("12345")
            .build();

    @Test
    void getAllEmployees_shouldReturnList() throws Exception {
        Mockito.when(employeeService.getEmployees()).thenReturn(List.of(sampleResponse));

        mockMvc.perform(get("/api/v1/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"));
    }

    @Test
    void getEmployeeById_shouldReturnEmployee() throws Exception {
        Mockito.when(employeeService.getEmployeeByEmployeeId("1")).thenReturn(sampleResponse);

        mockMvc.perform(get("/api/v1/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.emailAddress").value("john@example.com"));
    }

    @Test
    void addEmployee_shouldReturnCreatedEmployee() throws Exception {
        Mockito.when(employeeService.addEmployee(any())).thenReturn(sampleResponse);

        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    void updateEmployee_shouldReturnUpdatedEmployee() throws Exception {
        Mockito.when(employeeService.updateEmployee(eq("1"), any())).thenReturn(sampleResponse);

        mockMvc.perform(put("/api/v1/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void deleteEmployee_shouldReturnNoContent() throws Exception {
        Mockito.doNothing().when(employeeService).deleteEmployee("1");

        mockMvc.perform(delete("/api/v1/employees/1"))
                .andExpect(status().isNoContent());
    }
}
