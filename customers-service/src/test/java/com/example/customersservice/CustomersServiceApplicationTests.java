package com.example.customersservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class CustomersServiceApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void main_runsSuccessfully() {
        assertDoesNotThrow(() -> CustomersServiceApplication.main(new String[]{}));
    }
}
