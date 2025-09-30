package com.example.apigatewayservice;

import com.example.apigatewayservice.presentationlayer.customerdto.CustomerRequestModel;
import com.example.apigatewayservice.presentationlayer.customerdto.PhoneNumber;
import com.example.apigatewayservice.presentationlayer.customerdto.PhoneType;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerRequestModelTest {
    @Test
    void allGettersAndSettersShouldWork() {
        CustomerRequestModel customer = new CustomerRequestModel();

        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmailAddress("john.doe@example.com");
        customer.setStreetAddress("123 Maple St");
        customer.setCity("Montreal");
        customer.setProvince("QC");
        customer.setCountry("Canada");
        customer.setPostalCode("H3Z2Y7");

        List<PhoneNumber> numbers = Arrays.asList(
                new PhoneNumber(PhoneType.MOBILE, "514-123-4567"),
                new PhoneNumber(PhoneType.HOME, "438-987-6543")
        );
        customer.setPhoneNumbers(numbers);

        assertEquals("John", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
        assertEquals("john.doe@example.com", customer.getEmailAddress());
        assertEquals("123 Maple St", customer.getStreetAddress());
        assertEquals("Montreal", customer.getCity());
        assertEquals("QC", customer.getProvince());
        assertEquals("Canada", customer.getCountry());
        assertEquals("H3Z2Y7", customer.getPostalCode());
        assertEquals(numbers, customer.getPhoneNumbers());
    }
    @Test
    void allArgsConstructorShouldInitializeCorrectly() {
        List<PhoneNumber> phones = List.of(
                new PhoneNumber(PhoneType.MOBILE, "514-999-8888")
        );

        CustomerRequestModel customer = new CustomerRequestModel(
                "Jane",
                "Smith",
                "jane.smith@example.com",
                "456 Oak Ave",
                "Quebec City",
                "QC",
                "Canada",
                "G1R2K5",
                phones
        );

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

}
