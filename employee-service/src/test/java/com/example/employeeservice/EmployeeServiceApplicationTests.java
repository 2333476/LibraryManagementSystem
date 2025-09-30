package com.example.employeeservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("h2")
class EmployeeServiceApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testMainMethod() {
        System.setProperty("spring.profiles.active", "h2");
        EmployeeServiceApplication.main(new String[] {});
    }

}
