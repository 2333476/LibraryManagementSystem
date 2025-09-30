package com.example.apigatewayservice;
import com.example.apigatewayservice.businesslayer.EmployeeServiceImpl;
import com.example.apigatewayservice.domainclientlayer.EmployeeServiceClient;
import com.example.apigatewayservice.presentationlayer.employeedto.Department;
import com.example.apigatewayservice.presentationlayer.employeedto.EmployeeRequestModel;
import com.example.apigatewayservice.presentationlayer.employeedto.EmployeeResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceImplTest {
    private EmployeeServiceClient employeeServiceClient;
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
        employeeServiceClient = mock(EmployeeServiceClient.class);
        employeeService = new EmployeeServiceImpl(employeeServiceClient);
    }

    @Test
    void getEmployees_shouldReturnListOfEmployees() {
        // Arrange
        EmployeeResponseModel employee = EmployeeResponseModel.builder()
                .employeeId("emp123")
                .firstName("John")
                .lastName("Doe")
                .emailAddress("john.doe@example.com")
                .phoneNumber("1234567890")
                .salary(75000.0)
                .commissionRate(0.1)
                .department(Department.Sales)
                .streetAddress("123 Main St")
                .city("Cityville")
                .province("Quebec")
                .country("Canada")
                .postalCode("A1B2C3")
                .build();

        List<EmployeeResponseModel> expectedList = List.of(employee);
        when(employeeServiceClient.getAllEmployees()).thenReturn(expectedList);

        // Act
        List<EmployeeResponseModel> actualList = employeeService.getEmployees();

        // Assert
        assertEquals(expectedList, actualList);
        verify(employeeServiceClient).getAllEmployees();
    }

    @Test
    void getEmployeeByEmployeeId_shouldReturnEmployee() {
        String employeeId = "emp123";
        EmployeeResponseModel expected = EmployeeResponseModel.builder()
                .employeeId(employeeId)
                .firstName("Alice")
                .lastName("Smith")
                .emailAddress("alice@example.com")
                .phoneNumber("5555555555")
                .salary(60000.0)
                .commissionRate(0.05)
                .department(Department.Sales)
                .streetAddress("456 Elm St")
                .city("Montreal")
                .province("Quebec")
                .country("Canada")
                .postalCode("H2Z1X4")
                .build();

        when(employeeServiceClient.getEmployeeByEmployeeId(employeeId)).thenReturn(expected);

        EmployeeResponseModel actual = employeeService.getEmployeeByEmployeeId(employeeId);

        assertEquals(expected, actual);
        verify(employeeServiceClient).getEmployeeByEmployeeId(employeeId);
    }

    @Test
    void addEmployee_shouldReturnCreatedEmployee() {
        EmployeeRequestModel request = EmployeeRequestModel.builder()
                .firstName("Bob")
                .lastName("Taylor")
                .emailAddress("bob@example.com")
                .phoneNumber("1112223333")
                .salary(72000.0)
                .commissionRate(0.07)
                .department(Department.Sales)
                .streetAddress("789 Oak St")
                .city("Quebec City")
                .province("Quebec")
                .country("Canada")
                .postalCode("G1R4P5")
                .build();
        EmployeeResponseModel expected = EmployeeResponseModel.builder()
                .employeeId("emp456")
                .firstName("Bob")
                .lastName("Taylor")
                .emailAddress("bob@example.com")
                .phoneNumber("1112223333")
                .salary(72000.0)
                .commissionRate(0.07)
                .department(Department.AccountsReceivable)
                .streetAddress("789 Oak St")
                .city("Quebec City")
                .province("Quebec")
                .country("Canada")
                .postalCode("G1R4P5")
                .build();

        when(employeeServiceClient.addEmployee(request)).thenReturn(expected);

        EmployeeResponseModel actual = employeeService.addEmployee(request);

        assertEquals(expected, actual);
        verify(employeeServiceClient).addEmployee(request);
    }

    @Test
    void updateEmployee_shouldReturnUpdatedEmployee() {
        String employeeId = "emp789";
        EmployeeRequestModel request = EmployeeRequestModel.builder()
                .firstName("Bob")
                .lastName("Taylor")
                .emailAddress("bob@example.com")
                .phoneNumber("1112223333")
                .salary(72000.0)
                .commissionRate(0.07)
                .department(Department.Marketing)
                .streetAddress("789 Oak St")
                .city("Quebec City")
                .province("Quebec")
                .country("Canada")
                .postalCode("G1R4P5")
                .build();
        EmployeeResponseModel expected = EmployeeResponseModel.builder()
                .employeeId(employeeId)
                .firstName("Eve")
                .lastName("Wong")
                .emailAddress("eve@example.com")
                .phoneNumber("9876543210")
                .salary(80000.0)
                .commissionRate(0.12)
                .department(Department.Marketing)
                .streetAddress("321 Pine St")
                .city("Laval")
                .province("Quebec")
                .country("Canada")
                .postalCode("J7V9L3")
                .build();

        when(employeeServiceClient.updateEmployee(employeeId, request)).thenReturn(expected);

        EmployeeResponseModel actual = employeeService.updateEmployee(employeeId, request);

        assertEquals(expected, actual);
        verify(employeeServiceClient).updateEmployee(employeeId, request);
    }

    @Test
    void deleteEmployee_shouldCallDeleteMethod() {
        String employeeId = "emp999";

        doNothing().when(employeeServiceClient).deleteEmployee(employeeId);

        employeeService.deleteEmployee(employeeId);

        verify(employeeServiceClient).deleteEmployee(employeeId);
    }
}
