package com.example.customersservice;

import com.example.customersservice.businesslogiclayer.CustomerServiceImpl;
import com.example.customersservice.dataaccesslayer.Customer;
import com.example.customersservice.presentationlayer.CustomerResponseModel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class CustomerServiceImplMapperTest {
    CustomerServiceImpl service = new CustomerServiceImpl(null, null, null); // Pass mocks or nulls if unused

    @Test
    void testFromEntityToModel() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");

        CustomerResponseModel response = service.fromEntityToModel(customer);

        assertNotNull(response);
        assertEquals("John", response.getFirstName());
        assertEquals("Doe", response.getLastName());
    }

    @Test
    void testFromEntityListToModelList() {
        Customer customer1 = new Customer();
        customer1.setFirstName("Alice");
        customer1.setLastName("Smith");

        Customer customer2 = new Customer();
        customer2.setFirstName("Bob");
        customer2.setLastName("Brown");

        List<CustomerResponseModel> result = service.fromEntityListToModelList(List.of(customer1, customer2));

        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getFirstName());
        assertEquals("Bob", result.get(1).getFirstName());
    }
}
