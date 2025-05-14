package com.example.loanservice;

import com.example.loanservice.datamapperlayer.customerdto.PhoneType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneTypeTest {

    @Test
    void allEnumValuesShouldExist() {
        PhoneType[] values = PhoneType.values();

        // On vérifie qu'il y a au moins WORK et MOBILE, peu importe s’il y en a d'autres
        List<PhoneType> types = List.of(values);
        assertTrue(types.contains(PhoneType.WORK));
        assertTrue(types.contains(PhoneType.MOBILE));
    }

    @Test
    void valueOfShouldReturnCorrectEnum() {
        assertEquals(PhoneType.WORK, PhoneType.valueOf("WORK"));
        assertEquals(PhoneType.MOBILE, PhoneType.valueOf("MOBILE"));
    }
}
