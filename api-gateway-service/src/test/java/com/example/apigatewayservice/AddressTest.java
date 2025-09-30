package com.example.apigatewayservice;

import com.example.apigatewayservice.presentationlayer.customerdto.Address;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddressTest {
    @Test
    void constructorAndGettersShouldWork() {
        Address address = new Address("123 Maple St", "Montreal", "QC", "Canada", "H3Z2Y7");

        assertEquals("123 Maple St", address.getStreetAddress());
        assertEquals("Montreal", address.getCity());
        assertEquals("QC", address.getProvince());
        assertEquals("Canada", address.getCountry());
        assertEquals("H3Z2Y7", address.getPostalCode());
    }

    @Test
    void settersShouldUpdateValues() {
        Address address = new Address();
        address.setStreetAddress("456 Maple Ave");
        address.setCity("Toronto");
        address.setProvince("ON");
        address.setPostalCode("M4B1B3");
        address.setCountry("Canada");

        assertEquals("456 Maple Ave", address.getStreetAddress());
        assertEquals("Toronto", address.getCity());
        assertEquals("ON", address.getProvince());
        assertEquals("M4B1B3", address.getPostalCode());
        assertEquals("Canada", address.getCountry());
    }

    @Test
    void equalsAndHashCodeShouldWork() {
        Address a1 = new Address("789 Elm St", "Ottawa", "ON", "K1A0B1", "Canada");
        Address a2 = new Address("789 Elm St", "Ottawa", "ON", "K1A0B1", "Canada");
        Address a3 = new Address("Different", "City", "QC", "X0X0X0", "Canada");

        assertEquals(a1, a2);
        assertEquals(a1.hashCode(), a2.hashCode());
        assertNotEquals(a1, a3);
        assertNotEquals(a1.hashCode(), a3.hashCode());
    }
}
