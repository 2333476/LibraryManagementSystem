package com.example.customersservice;

import com.example.customersservice.businesslogiclayer.CustomerServiceImpl;
import com.example.customersservice.dataaccesslayer.*;
import com.example.customersservice.presentationlayer.CustomerRequestModel;
import com.example.customersservice.presentationlayer.CustomerResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = {"spring.datasource.url=jdbc:h2:mem:customer_test_db"})
@ActiveProfiles("h2")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CustomerServiceIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerServiceImpl customerService;

    private Customer savedCustomer;

    @BeforeEach
    void setUp() {
        Address address = new Address("123 Main", "City", "QC", "H1A1A1");
        CustomerIdentifier identifier = new CustomerIdentifier();

        Customer customer = new Customer(identifier, "John", "Doe", "john.doe@example.com", address);
        customer.setUsername("johndoe");
        customer.setPassword("pw123");
        customer.setPhoneNumbers(List.of(new PhoneNumber(PhoneType.MOBILE, "514-123-4567")));

        savedCustomer = customerRepository.save(customer);
    }

    // ✅ Tests positifs

    @Test
    void getCustomers_returnsList() {
        List<CustomerResponseModel> customers = customerService.getCustomers();
        assertFalse(customers.isEmpty());
    }

    @Test
    void getCustomerByCustomerId_returnsCorrectCustomer() {
        CustomerResponseModel result = customerService.getCustomerbyCustomerId(savedCustomer.getCustomerIdentifier().getCustomerId());
        assertEquals("John", result.getFirstName());
    }

    @Test
    void addCustomer_validData_returnsSavedCustomer() {
        CustomerRequestModel request = new CustomerRequestModel(
                "Smith", "Alice", "alice@example.com",
                "456 Elm", "H2B2B2", "Montreal", "QC",
                "alicesmith", "pw123", "pw123",
                List.of(new PhoneNumber(PhoneType.WORK, "514-000-1111"))
        );

        CustomerResponseModel created = customerService.addCustomer(request);
        assertEquals("Alice", created.getFirstName());
    }

    @Test
    void updateCustomer_validData_returnsUpdatedCustomer() {
        CustomerRequestModel request = new CustomerRequestModel(
                "Updated", "User", "updated@example.com",
                "789 Updated", "H4H4H4", "UpdatedCity", "QC",
                "updateduser", "pw456", "pw456",
                List.of(new PhoneNumber(PhoneType.HOME, "514-999-9999"))
        );

        CustomerResponseModel updated = customerService.updateCustomer(savedCustomer.getCustomerIdentifier().getCustomerId(), request);
        assertEquals("User", updated.getFirstName());
    }

    @Test
    void deleteCustomer_removesCustomer() {
        String response = customerService.deleteCustomerbyCustomerId(savedCustomer.getCustomerIdentifier().getCustomerId());
        assertTrue(response.contains("deleted"));
        assertNull(customerRepository.findCustomerByCustomerIdentifier(savedCustomer.getCustomerIdentifier()));
    }

    // ❌ Tests négatifs

    @Test
    void addCustomer_passwordMismatch_throwsException() {
        CustomerRequestModel request = new CustomerRequestModel(
                "Smith", "Error", "error@example.com",
                "456 Elm", "H2B2B2", "Montreal", "QC",
                "erroruser", "pw123", "mismatch",
                List.of(new PhoneNumber(PhoneType.HOME, "514-000-0000"))
        );

        assertThrows(RuntimeException.class, () -> customerService.addCustomer(request));
    }

    @Test
    void addCustomer_missingAddress_throwsException() {
        CustomerRequestModel request = new CustomerRequestModel(
                "Smith", "Jane", "jane@example.com",
                null, null, null, null,
                "janesmith", "pw123", "pw123",
                List.of(new PhoneNumber(PhoneType.WORK, "514-000-0001"))
        );

        assertThrows(IllegalArgumentException.class, () -> customerService.addCustomer(request));
    }

    @Test
    void getCustomerByCustomerId_invalidId_throwsNotFoundException() {
        String invalidId = "00000000-0000-0000-0000-000000000000";
        assertThrows(RuntimeException.class, () -> customerService.getCustomerbyCustomerId(invalidId));
    }

    @Test
    void deleteCustomer_invalidId_returnsNotFoundMessage() {
        String response = customerService.deleteCustomerbyCustomerId("00000000-0000-0000-0000-000000000000");
        assertTrue(response.contains("not found"));
    }
}
