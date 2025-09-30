package com.example.apigatewayservice;

import com.example.apigatewayservice.presentationlayer.customerdto.CustomerResponseModel;
import com.example.apigatewayservice.presentationlayer.customerdto.PhoneNumber;
import com.example.apigatewayservice.presentationlayer.customerdto.PhoneType;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerResponseModelTest {
    @Test
    void constructorAndGettersShouldWork() {
        List<PhoneNumber> phones = List.of(
                new PhoneNumber(PhoneType.MOBILE, "514-999-8888")
        );

        CustomerResponseModel customer = CustomerResponseModel.builder()
                .customerId("cust001")
                .firstName("Jane")
                .lastName("Smith")
                .emailAddress("jane.smith@example.com")
                .streetAddress("456 Oak Ave")
                .city("Quebec City")
                .province("QC")
                .country("Canada")
                .postalCode("G1R2K5")
                .phoneNumbers(phones)
                .build();

        assertEquals("cust001", customer.getCustomerId());
        assertEquals("Jane", customer.getFirstName());
        assertEquals("Smith", customer.getLastName());
        assertEquals("jane.smith@example.com", customer.getEmailAddress());
        assertEquals("456 Oak Ave", customer.getStreetAddress());
        assertEquals("Quebec City", customer.getCity());
        assertEquals("QC", customer.getProvince());
        assertEquals("Canada", customer.getCountry());
        assertEquals("G1R2K5", customer.getPostalCode());
        assertEquals(phones, customer.getPhoneNumbers());
    }

    @Test
    void builderShouldWork() {
        List<PhoneNumber> phones = Arrays.asList(
                new PhoneNumber(PhoneType.WORK, "999-999-9999")
        );

        CustomerResponseModel customer = CustomerResponseModel.builder()
                .customerId("builder123")
                .firstName("Alice")
                .lastName("Smith")
                .emailAddress("alice@example.com")
                .streetAddress("789 Pine Rd")
                .city("Quebec City")
                .province("QC")
                .country("Canada")
                .postalCode("G1A1A1")
                .phoneNumbers(phones)
                .build();

        assertEquals("builder123", customer.getCustomerId());
        assertEquals("Alice", customer.getFirstName());
        assertEquals("Smith", customer.getLastName());
        assertEquals("alice@example.com", customer.getEmailAddress());
        assertEquals("789 Pine Rd", customer.getStreetAddress());
        assertEquals("Quebec City", customer.getCity());
        assertEquals("QC", customer.getProvince());
        assertEquals("Canada", customer.getCountry());
        assertEquals("G1A1A1", customer.getPostalCode());
        assertEquals(phones, customer.getPhoneNumbers());
    }
}
