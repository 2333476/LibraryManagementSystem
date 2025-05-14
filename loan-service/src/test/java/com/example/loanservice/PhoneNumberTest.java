package com.example.loanservice;

import com.example.loanservice.datamapperlayer.customerdto.PhoneNumber;
import com.example.loanservice.datamapperlayer.customerdto.PhoneType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneNumberTest {

    @Test
    void phoneNumberShouldStoreTypeAndNumberCorrectly() {
        PhoneNumber phoneNumber = new PhoneNumber(PhoneType.WORK, "514-555-1234");

        assertEquals(PhoneType.WORK, phoneNumber.getType());
        assertEquals("514-555-1234", phoneNumber.getNumber());
    }

    @Test
    void phoneNumberEqualsAndHashCodeShouldWork() {
        PhoneNumber p1 = new PhoneNumber(PhoneType.MOBILE, "514-123-4567");
        PhoneNumber p2 = new PhoneNumber(PhoneType.MOBILE, "514-123-4567");

        assertEquals(p1, p2);              // compare par contenu
        assertEquals(p1.hashCode(), p2.hashCode()); // compare les hashcodes aussi
    }
}
