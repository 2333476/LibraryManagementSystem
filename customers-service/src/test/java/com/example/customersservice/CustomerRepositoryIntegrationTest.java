package com.example.customersservice;

import com.example.customersservice.dataaccesslayer.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("h2")
public class CustomerRepositoryIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    private Customer savedCustomer;

    @BeforeEach
    void setup() {
        Address address = new Address("456 Rue Test", "Montreal", "QC", "H2X3X3");
        CustomerIdentifier identifier = new CustomerIdentifier();
        Customer customer = new Customer(identifier, "John", "Doe", "john@example.com", address);

        customer.setUsername("johndoe");
        customer.setPassword("secret123");
        customer.setPhoneNumbers(List.of(new PhoneNumber(PhoneType.MOBILE, "514-123-4567")));

        savedCustomer = customerRepository.save(customer);
    }

    @Test
    void testFindCustomerByCustomerIdentifier_success() {
        Customer found = customerRepository.findCustomerByCustomerIdentifier(savedCustomer.getCustomerIdentifier());
        assertNotNull(found);
        assertEquals(savedCustomer.getEmailAddress(), found.getEmailAddress());
    }

    @Test
    void testFindCustomerByEmailAddress_success() {
        Customer found = customerRepository.findCustomerByEmailAddress("john@example.com");
        assertNotNull(found);
        assertEquals(savedCustomer.getCustomerIdentifier().getCustomerId(), found.getCustomerIdentifier().getCustomerId());
    }

    @Test
    void testSaveCustomer_success() {
        assertNotNull(savedCustomer.getId());
        assertEquals("John", savedCustomer.getFirstName());
    }

    @Test
    void testDeleteCustomer_success() {
        customerRepository.delete(savedCustomer);
        Customer deleted = customerRepository.findCustomerByCustomerIdentifier(savedCustomer.getCustomerIdentifier());
        assertNull(deleted);
    }

    @Test
    void testFindAllCustomers_returnsList() {
        List<Customer> customers = customerRepository.findAll();
        assertFalse(customers.isEmpty());
    }
}
