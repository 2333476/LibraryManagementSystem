package com.example.customersservice.dataaccesslayer;

import com.example.customersservice.dataaccesslayer.Address;
import com.example.customersservice.dataaccesslayer.Customer;
import com.example.customersservice.dataaccesslayer.CustomerIdentifier;
import com.example.customersservice.dataaccesslayer.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("Save customer and find by id")
    void shouldSaveAndFindCustomer() {
        // Arrange
        CustomerIdentifier identifier = new CustomerIdentifier("test-id");
        Address address = new Address("123 Main St", "Cityville", "ProvinceX", "A1B2C3");
        Customer customer = new Customer(identifier, "John", "Doe", "john@example.com", address);

        // Act
        Customer saved = customerRepository.save(customer);
        Customer found = customerRepository.findById(saved.getId()).orElse(null);

        // Assert
        assertThat(found).isNotNull();
        assertThat(found.getFirstName()).isEqualTo("John");
        assertThat(found.getCustomerAddress().getStreetAddress()).isEqualTo("123 Main St");
    }
}
