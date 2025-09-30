package com.example.apigatewayservice;

import com.example.apigatewayservice.presentationlayer.customerdto.PhoneNumber;
import com.example.apigatewayservice.presentationlayer.customerdto.PhoneType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneNumberTest {
    @Test
    void constructorAndGettersShouldWork() {
        PhoneNumber phoneNumber = new PhoneNumber(PhoneType.MOBILE, "514-123-4567");

        assertEquals(PhoneType.MOBILE, phoneNumber.getType());
        assertEquals("514-123-4567", phoneNumber.getNumber());
    }

    @Test
    void settersShouldWork() {
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setType(PhoneType.HOME);
        phoneNumber.setNumber("438-987-6543");

        assertEquals(PhoneType.HOME, phoneNumber.getType());
        assertEquals("438-987-6543", phoneNumber.getNumber());
    }

    @Test
    void equalsAndHashCodeShouldWork() {
        PhoneNumber p1 = new PhoneNumber(PhoneType.MOBILE, "514-123-4567");
        PhoneNumber p2 = new PhoneNumber(PhoneType.MOBILE, "514-123-4567");

        assertEquals(p1, p2); // passes if equals() is overridden correctly
        assertEquals(p1.hashCode(), p2.hashCode());
    }
}
