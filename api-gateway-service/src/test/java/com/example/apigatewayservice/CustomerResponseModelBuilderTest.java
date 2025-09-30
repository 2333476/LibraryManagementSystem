package com.example.apigatewayservice;

import com.example.apigatewayservice.presentationlayer.customerdto.CustomerResponseModel;
import com.example.apigatewayservice.presentationlayer.customerdto.PhoneNumber;
import com.example.apigatewayservice.presentationlayer.customerdto.PhoneType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerResponseModelBuilderTest {
    @Test
    void builderShouldBuildCorrectly() {
        List<PhoneNumber> phones = List.of(
                new PhoneNumber(PhoneType.WORK, "514-123-4567"),
                new PhoneNumber(PhoneType.HOME, "438-987-6543")
        );

        CustomerResponseModel customer = CustomerResponseModel.builder()
                .customerId("cust001")
                .firstName("Jane")
                .lastName("Smith")
                .emailAddress("jane.smith@example.com")
                .streetAddress("123 Maple St")
                .city("Montreal")
                .province("QC")
                .country("Canada")
                .postalCode("H3Z2Y7")
                .phoneNumbers(phones)
                .build();

        assertEquals("cust001", customer.getCustomerId());
        assertEquals("Jane", customer.getFirstName());
        assertEquals("Smith", customer.getLastName());
        assertEquals("jane.smith@example.com", customer.getEmailAddress());
        assertEquals("123 Maple St", customer.getStreetAddress());
        assertEquals("Montreal", customer.getCity());
        assertEquals("QC", customer.getProvince());
        assertEquals("Canada", customer.getCountry());
        assertEquals("H3Z2Y7", customer.getPostalCode());
        assertEquals(phones, customer.getPhoneNumbers());
    }
}
