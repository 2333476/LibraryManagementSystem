package com.example.apigatewayservice;
import com.example.apigatewayservice.domainclientlayer.EmployeeServiceClient;
import com.example.apigatewayservice.presentationlayer.employeedto.Department;
import com.example.apigatewayservice.presentationlayer.employeedto.EmployeeRequestModel;
import com.example.apigatewayservice.presentationlayer.employeedto.EmployeeResponseModel;
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

public class EmployeeServiceClientTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private EmployeeServiceClient employeeServiceClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employeeServiceClient = new EmployeeServiceClient(restTemplate, "localhost", "8082");
    }

    @Test
    void testGetAllEmployees() {
        List<EmployeeResponseModel> mockList = List.of(
                new EmployeeResponseModel(
                        "emp-001", "Alice", "Johnson", "alice@example.com", "514-555-1234",
                        75000.0, 0.05, Department.Marketing,
                        "123 Dev St", "Montreal", "QC", "Canada", "H2X 1Y4"
                )
        );

        ResponseEntity<List<EmployeeResponseModel>> response = new ResponseEntity<>(mockList, HttpStatus.OK);

        when(restTemplate.exchange(
                eq("http://localhost:8082/api/v1/employees"),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class)
        )).thenReturn(response);

        List<EmployeeResponseModel> result = employeeServiceClient.getAllEmployees();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetEmployeeById() {
        String id = "emp-123";
        EmployeeResponseModel mockResponse = new EmployeeResponseModel(
                "emp-123",
                "Alice",
                "Walker",
                "alice.walker@example.com",
                "514-123-4567",
                75000.00,
                0.10,
                Department.Marketing,
                "123 Tech Lane",
                "Montreal",
                "QC",
                "Canada",
                "H3Z2Y7"
        );

        when(restTemplate.exchange(
                contains(id),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class)
        )).thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        EmployeeResponseModel result = employeeServiceClient.getEmployeeByEmployeeId(id);

        assertNotNull(result);
    }

    @Test
    void testAddEmployee() {
        EmployeeRequestModel request = new EmployeeRequestModel(
                "Alice", "Walker", "alice.walker@example.com", "514-123-4567",
                75000.0, 0.10,
                Department.AccountsReceivable,
                "123 Tech Lane", "Montreal", "QC", "Canada", "H3Z2Y7"
        );
        EmployeeResponseModel mockResponse = new EmployeeResponseModel("e5913a79-9b1e-4516-9ffd-06578e7af261", "Vilma", "Chawner", "vchawner0@phoca.cz", "185-415-8773", 34000.0, 0.0, Department.Sales, "8452 Anhalt Park", "Chambly", "Québec", "Canada", "J3L 5Y6");

        when(restTemplate.postForObject(
                anyString(), eq(request), eq(EmployeeResponseModel.class))
        ).thenReturn(mockResponse);

        EmployeeResponseModel result = employeeServiceClient.addEmployee(request);

        assertNotNull(result);
    }

    @Test
    void testUpdateEmployee() {
        String id = "emp-321";
        EmployeeRequestModel request = new EmployeeRequestModel("Dorry", "Gepp", "dgepp1@stanford.edu", "964-472-9806", 75000.0, 3.5, Department.Sales, "23320 Pankratz Park", "Barrhead", "Alberta", "Canada", "T7N 1S3");
        EmployeeResponseModel updated = new EmployeeResponseModel("bfe6b6f3-9316-4886-b150-f42eb8d91ef0", "Hunfredo", "Fellgett", "hfellgett2@biblegateway.com", "190-551-7108", 60000.0, 0.0, Department.Marketing, "275 Bellgrove Circle", "Roberval", "Québec", "Canada", "G8H 0H0");

        when(restTemplate.exchange(
                contains(id),
                eq(HttpMethod.PUT),
                any(HttpEntity.class),
                eq(EmployeeResponseModel.class)
        )).thenReturn(new ResponseEntity<>(updated, HttpStatus.OK));

        EmployeeResponseModel result = employeeServiceClient.updateEmployee(id, request);

        assertNotNull(result);
        assertEquals(updated, result);
    }

    @Test
    void testDeleteEmployee() {
        String id = "emp-999";

        when(restTemplate.exchange(
                eq("http://localhost:8082/api/v1/employees/123"),
                eq(HttpMethod.DELETE),
                any(HttpEntity.class),
                eq(Void.class)
        )).thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

        assertDoesNotThrow(() -> employeeServiceClient.deleteEmployee(id));
    }
}
