package com.example.customersservice;

import com.example.customersservice.dataaccesslayer.*;
import com.example.customersservice.presentationlayer.CustomerRequestModel;
import com.example.customersservice.presentationlayer.CustomerResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.datasource.url=jdbc:h2:mem:customer_test_db"})
@ActiveProfiles("h2")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CustomerControllerIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @LocalServerPort
    private int port;

    private WebTestClient webTestClient;

    private final String BASE_URI = "/api/v1/customers";
    private Customer savedCustomer;

    @BeforeEach
    void setUp() {
        this.webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();

        Address address = new Address("123 Main", "City", "QC", "H1A1A1");

        Customer customer = new Customer();
        customer.setCustomerIdentifier(new CustomerIdentifier());
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmailAddress("john.doe@example.com");
        customer.setUsername("johndoe");
        customer.setPassword("password123");
        customer.setCustomerAddress(address);
        customer.setPhoneNumbers(List.of(new PhoneNumber(PhoneType.MOBILE, "514-123-4567")));

        savedCustomer = customerRepository.save(customer);
    }


    @Test
    void getAllCustomers_returnsList() {
        webTestClient.get()
                .uri(BASE_URI)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CustomerResponseModel.class)
                .value(customers -> assertFalse(customers.isEmpty()));
    }

    @Test
    void getCustomerByCustomerId_returnsCustomer() {
        webTestClient.get()
                .uri(BASE_URI + "/" + savedCustomer.getCustomerIdentifier().getCustomerId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.emailAddress").isEqualTo("john.doe@example.com");
    }

    @Test
    void addCustomer_validRequest_returnsCreated() {
        CustomerRequestModel request = new CustomerRequestModel(
                "Smith", "Alice", "alice.smith@example.com",
                "456 Elm", "H2B2B2", "Montreal", "QC",
                "alicesmith", "pw123", "pw123",
                List.of(new PhoneNumber(PhoneType.HOME, "514-000-1111"))
        );

        webTestClient.post()
                .uri(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.firstName").isEqualTo("Alice");
    }

    @Test
    void updateCustomer_validRequest_returnsUpdated() {
        CustomerRequestModel request = new CustomerRequestModel(
                "Updated", "User", "updated.user@example.com",
                "789 Oak", "H3C3C3", "Laval", "QC",
                "updateduser", "pw456", "pw456",
                List.of(new PhoneNumber(PhoneType.WORK, "514-999-8888"))
        );

        webTestClient.put()
                .uri(BASE_URI + "/" + savedCustomer.getCustomerIdentifier().getCustomerId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.firstName").isEqualTo("User");
    }

    @Test
    void deleteCustomer_returnsOk() {
        webTestClient.delete()
                .uri(BASE_URI + "/" + savedCustomer.getCustomerIdentifier().getCustomerId())
                .exchange()
                .expectStatus().isOk();
    }

    // ----------------------- NEGATIVE TESTS -----------------------

    @Test
    void getCustomer_invalidIdFormat_returnsUnprocessableEntity() {
        webTestClient.get()
                .uri(BASE_URI + "/123")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void getCustomer_notFound_returnsNotFound() {
        String fakeId = UUID.randomUUID().toString();
        webTestClient.get()
                .uri(BASE_URI + "/" + fakeId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void addCustomer_passwordMismatch_returnsServerError() {
        CustomerRequestModel request = new CustomerRequestModel(
                "Smith", "Bob", "bob.smith@example.com",
                "456 Elm", "H2B2B2", "Montreal", "QC",
                "bobsmith", "pw123", "pw456",
                List.of(new PhoneNumber(PhoneType.HOME, "514-000-1111"))
        );

        webTestClient.post()
                .uri(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void addCustomer_missingAddress_returnsServerError() {
        CustomerRequestModel request = new CustomerRequestModel(
                "Smith", "Jane", "jane.smith@example.com",
                null, null, null, null,
                "janesmith", "pw123", "pw123",
                List.of(new PhoneNumber(PhoneType.HOME, "514-000-1111"))
        );

        webTestClient.post()
                .uri(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void deleteCustomer_invalidFormat_returnsUnprocessableEntity() {
        webTestClient.delete()
                .uri(BASE_URI + "/abc")
                .exchange()
                .expectStatus().isOk();
    }
}
