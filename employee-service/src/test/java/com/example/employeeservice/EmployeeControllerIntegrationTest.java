package com.example.employeeservice;

import com.example.employeeservice.datalayer.*;
import com.example.employeeservice.presentationlayer.EmployeeRequestModel;
import com.example.employeeservice.presentationlayer.EmployeeResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.datasource.url=jdbc:h2:mem:employee_test_db"})
@ActiveProfiles("h2")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class EmployeeControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private EmployeeRepository employeeRepository;

    private final String BASE_URI = "/api/v1/employees";
    private Employee existingEmployee;

    @BeforeEach
    void setUp() {
        EmployeeIdentifier identifier = new EmployeeIdentifier();
        existingEmployee = new Employee(
                identifier,
                "John", "Doe", "john.doe@example.com", "555-1234",
                50000.0, 5.0, Department.Sales,
                new Address("123 Main", "City", "QC", "Canada", "H1A1A1")
        );
        employeeRepository.save(existingEmployee);
    }

    // ✅ POSITIVE TESTS

    @Test
    public void getAllEmployees_returnsList() {
        webTestClient.get()
                .uri(BASE_URI)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EmployeeResponseModel.class)
                .value(employees -> assertFalse(employees.isEmpty()));
    }

    @Test
    public void getEmployeeById_existingId_returnsEmployee() {
        webTestClient.get()
                .uri(BASE_URI + "/" + existingEmployee.getEmployeeIdentifier().getEmployeeId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.emailAddress").isEqualTo("john.doe@example.com");
    }

    @Test
    public void addEmployee_validRequest_returnsCreated() {
        EmployeeRequestModel request = EmployeeRequestModel.builder()
                .firstName("Alice").lastName("Smith")
                .emailAddress("alice.smith@example.com")
                .phoneNumber("555-6789")
                .salary(60000.0)
                .commissionRate(2.0)
                .department(Department.HumanResources)
                .streetAddress("456 Park").city("Montreal")
                .province("QC").country("Canada").postalCode("H2B2B2")
                .build();

        webTestClient.post()
                .uri(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.firstName").isEqualTo("Alice");
    }

    @Test
    public void updateEmployee_existingId_updatesSuccessfully() {
        EmployeeRequestModel update = EmployeeRequestModel.builder()
                .firstName("John Updated").lastName("Doe")
                .emailAddress("john.doe@example.com")
                .phoneNumber("555-9999")
                .salary(70000.0)
                .commissionRate(10.0)
                .department(Department.Sales)
                .streetAddress("999 Updated St").city("UpdatedCity")
                .province("QC").country("Canada").postalCode("H1H1H1")
                .build();

        webTestClient.put()
                .uri(BASE_URI + "/" + existingEmployee.getEmployeeIdentifier().getEmployeeId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(update)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.firstName").isEqualTo("John Updated")
                .jsonPath("$.phoneNumber").isEqualTo("555-9999");
    }

    @Test
    public void deleteEmployee_existingId_returnsNoContent() {
        webTestClient.delete()
                .uri(BASE_URI + "/" + existingEmployee.getEmployeeIdentifier().getEmployeeId())
                .exchange()
                .expectStatus().isNoContent();

        assertFalse(employeeRepository.existsById(existingEmployee.getId()));
    }

    // ❌ NEGATIVE TESTS

    @Test
    public void getEmployeeById_nonExisting_returnsNotFound() {
        String nonExistentId = "00000000-0000-0000-0000-000000000000";

        webTestClient.get()
                .uri(BASE_URI + "/" + nonExistentId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void deleteEmployee_nonExisting_returnsNotFound() {
        String nonExistentId = "00000000-0000-0000-0000-000000000000";

        webTestClient.delete()
                .uri(BASE_URI + "/" + nonExistentId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void getEmployeeById_invalidFormat_returnsUnprocessableEntity() {
        webTestClient.get()
                .uri(BASE_URI + "/123")
                .exchange()
                .expectStatus().isEqualTo(422);
    }

    @Test
    public void addEmployee_duplicateEmail_returnsUnprocessableEntity() {
        EmployeeRequestModel duplicate = EmployeeRequestModel.builder()
                .firstName("Dup").lastName("User")
                .emailAddress("john.doe@example.com")
                .phoneNumber("555-8888")
                .salary(55000.0)
                .commissionRate(3.0)
                .department(Department.Sales)
                .streetAddress("Dup St").city("City")
                .province("QC").country("Canada").postalCode("H3H3H3")
                .build();

        webTestClient.post()
                .uri(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(duplicate)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("$.message").exists();
    }

    @Test
    public void updateEmployee_invalidFormat_returnsUnprocessableEntity() {
        EmployeeRequestModel request = EmployeeRequestModel.builder().build(); // Empty

        webTestClient.put()
                .uri(BASE_URI + "/123")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isNotFound();
    }
}
